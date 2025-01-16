import React, { useEffect, useRef, useState } from "react";
import Peer from "simple-peer";
import io from "socket.io-client";
import { useAuth } from "../services/AuthContext";
import { WEBRTC_URL } from "../config";

function App() {
  const [me, setMe] = useState("");
  const [stream, setStream] = useState(null);
  const [receivingCall, setReceivingCall] = useState(false);
  const [caller, setCaller] = useState("");
  const [callerSignal, setCallerSignal] = useState(null);
  const [callAccepted, setCallAccepted] = useState(false);
  const [idToCall, setIdToCall] = useState("");
  const [callEnded, setCallEnded] = useState(false);
  const [name, setName] = useState("");
  const [videoEnabled, setVideoEnabled] = useState(true);
  const [audioEnabled, setAudioEnabled] = useState(true);

  const myVideo = useRef();
  const userVideo = useRef();
  const connectionRef = useRef();
  const socketRef = useRef();

  const { user } = useAuth();

  useEffect(() => {
    socketRef.current = io(WEBRTC_URL, {
      query: { userId: user?.id },
    });

    socketRef.current.on("me", (id) => setMe(id));

    const constraints = {
      video: {
        width: { ideal: 1280 },
        height: { ideal: 720 },
        frameRate: { ideal: 30 },
      },
      audio: true,
    };

    navigator.mediaDevices
      .getUserMedia(constraints)
      .then((currentStream) => {
        setStream(currentStream);
        if (myVideo.current) {
          myVideo.current.srcObject = currentStream;
        }
      })
      .catch((error) => console.error("Media devices error:", error));

    socketRef.current.on("callUser", (data) => {
      setReceivingCall(true);
      setCaller(data.from);
      setCallerSignal(data.signal);
    });

    socketRef.current.on("callEnded", () => {
      setCallEnded(true);
      if (connectionRef.current) connectionRef.current.destroy();
    });

    return () => {
      socketRef.current.disconnect();
    };
  }, [user]);

  const toggleVideo = () => {
    if (stream) {
      const videoTrack = stream.getVideoTracks()[0];
      videoTrack.enabled = !videoTrack.enabled;
      setVideoEnabled(videoTrack.enabled);
    }
  };

  const toggleAudio = () => {
    if (stream) {
      const audioTrack = stream.getAudioTracks()[0];
      audioTrack.enabled = !audioTrack.enabled;
      setAudioEnabled(audioTrack.enabled);
    }
  };

  const callUser = (id) => {
    const peer = new Peer({
      initiator: true,
      trickle: false,
      stream,
    });

    peer.on("signal", (data) => {
      socketRef.current.emit("callUser", {
        userId: id,
        signalData: data,
        from: me,
        name,
      });
    });

    peer.on("stream", (currentStream) => {
      if (currentStream) {
        console.log("Received stream from peer:", currentStream);
        if (userVideo.current) {
          userVideo.current.srcObject = currentStream;
        } else {
          console.log("userVideo.current not found");
        }
      } else {
        console.error("No stream received from peer");
      }
    });

    socketRef.current.on("callAccepted", (signal) => {
      setCallAccepted(true);
      peer.signal(signal);
    });

    connectionRef.current = peer;
  };

  const answerCall = () => {
    setCallAccepted(true);

    const peer = new Peer({
      initiator: false,
      trickle: false,
      stream,
    });

    peer.on("signal", (data) => {
      socketRef.current.emit("answerCall", { signal: data, to: caller });
    });

    peer.on("stream", (currentStream) => {
      if (currentStream) {
        console.log("Received stream from peer:", currentStream);
        if (userVideo.current) {
          userVideo.current.srcObject = currentStream;
        } else {
          console.log("userVideo.current not found");
        }
      } else {
        console.error("No stream received from peer");
      }
    });

    peer.signal(callerSignal);

    connectionRef.current = peer;
  };

  const leaveCall = () => {
    setCallEnded(true);
    try {
      if (connectionRef.current) {
        connectionRef.current.destroy(); // Safely destroy the peer
      }
    } catch (error) {
      console.error("Error destroying peer connection:", error);
    }
    socketRef.current.emit("callEnded", { to: caller || idToCall });
  };

  return (
    <div className="p-8 bg-gray-100 min-h-screen font-merriweather_sans">
      <h1 className="text-3xl font-bold text-gray-800 mb-6 text-center">
        WebRTC
      </h1>
      <div className="grid md:grid-cols-2 gap-8">
        <div className="flex flex-col items-center space-y-6">
          <div className="rounded-lg overflow-hidden shadow-lg bg-black w-full max-w-[500px] aspect-video">
            {stream && (
              <video
                playsInline
                muted
                ref={myVideo}
                autoPlay
                className="w-full h-full object-cover transform scale-x-[-1]"
              />
            )}
          </div>
          <div className="rounded-lg overflow-hidden shadow-lg bg-gray-800 w-full max-w-[500px] aspect-video">
            {callAccepted && !callEnded ? (
              <video
                playsInline
                ref={userVideo}
                autoPlay
                className="w-full h-full object-cover transform scale-x-[-1]"
              />
            ) : (
              <p className="text-gray-400 text-center p-4">No active video</p>
            )}
          </div>
        </div>

        <div className="bg-white p-6 rounded-lg shadow-lg space-y-6">
          <input
            placeholder="Enter your name"
            value={name}
            onChange={(e) => setName(e.target.value)}
            className="w-full p-3 border border-gray-300 rounded-lg"
          />
          <button
            onClick={() => navigator.clipboard.writeText(me)}
            className="text-blue-600 hover:text-blue-800"
          >
            Copy ID
          </button>

          <input
            placeholder="Enter ID to call"
            value={idToCall}
            onChange={(e) => setIdToCall(e.target.value)}
            className="w-full p-3 border border-gray-300 rounded-lg"
          />
          <div>
            {callAccepted && !callEnded ? (
              <button
                onClick={leaveCall}
                className="px-6 py-2 bg-red-500 text-white rounded-lg"
              >
                End Call
              </button>
            ) : (
              <button
                onClick={() => callUser(idToCall)}
                className="px-6 py-2 bg-green-500 text-white rounded-lg"
              >
                Call
              </button>
            )}
          </div>

          {receivingCall && !callAccepted && (
            <div className="p-4 bg-blue-50 rounded-lg">
              <h1>{name} is calling...</h1>
              <button
                onClick={answerCall}
                className="mt-4 px-4 py-2 bg-blue-500 text-white rounded-lg"
              >
                Answer
              </button>
            </div>
          )}
        </div>

        <div className="flex justify-center space-x-4">
          <button
            onClick={toggleVideo}
            className={`px-4 py-2 rounded-lg ${
              videoEnabled ? "bg-green-500 text-white" : "bg-gray-400"
            }`}
          >
            {videoEnabled ? "Video On" : "Video Off"}
          </button>
          <button
            onClick={toggleAudio}
            className={`px-4 py-2 rounded-lg ${
              audioEnabled ? "bg-green-500 text-white" : "bg-gray-400"
            }`}
          >
            {audioEnabled ? "Audio On" : "Audio Off"}
          </button>
        </div>
      </div>
    </div>
  );
}

export default App;
