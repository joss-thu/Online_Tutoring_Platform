import React, { useEffect, useRef, useState } from "react";
import Peer from "simple-peer";
import io from "socket.io-client";

const socket = io.connect("http://localhost:5000");
function App() {
  const [me, setMe] = useState("");
  const [stream, setStream] = useState();
  const [receivingCall, setReceivingCall] = useState(false);
  const [caller, setCaller] = useState("");
  const [callerSignal, setCallerSignal] = useState();
  const [callAccepted, setCallAccepted] = useState(false);
  const [idToCall, setIdToCall] = useState("");
  const [callEnded, setCallEnded] = useState(false);
  const [name, setName] = useState("");
  const myVideo = useRef();
  const userVideo = useRef();
  const connectionRef = useRef();

  useEffect(() => {
    navigator.mediaDevices
      .getUserMedia({ video: true, audio: true })
      .then((stream) => {
        setStream(stream);
        if (myVideo.current) {
          myVideo.current.srcObject = stream;
        }
      })
      .catch((error) => {
        console.error("Error accessing media devices:", error);
      });

    socket.on("me", (id) => {
      setMe(id);
    });

    socket.on("callUser", (data) => {
      setReceivingCall(true);
      setCaller(data.from);
      setName(data.name);
      setCallerSignal(data.signal);
    });
  }, []);

  const callUser = (id) => {
    const peer = new Peer({
      initiator: true,
      trickle: false,
      stream: stream,
    });
    peer.on("signal", (data) => {
      socket.emit("callUser", {
        userToCall: id,
        signalData: data,
        from: me,
        name: name,
      });
    });
    peer.on("stream", (stream) => {
      userVideo.current.srcObject = stream;
    });
    socket.on("callAccepted", (signal) => {
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
      stream: stream,
    });
    peer.on("signal", (data) => {
      socket.emit("answerCall", { signal: data, to: caller });
    });
    peer.on("stream", (stream) => {
      userVideo.current.srcObject = stream;
    });

    peer.signal(callerSignal);
    connectionRef.current = peer;
  };

  const leaveCall = () => {
    setCallEnded(true);
    connectionRef.current.destroy();

    // Notify the other user that the call has ended
    socket.emit("callEnded", { to: caller || idToCall });
  };

  return (
    <>
      <div className="p-8 bg-gray-100 min-h-screen">
        <h1 className="text-3xl font-bold text-gray-800 mb-6 text-center">
          WebRTC
        </h1>
        <div className="grid md:grid-cols-2 gap-8">
          {/* Video Section */}
          <div className="flex flex-col items-center space-y-6">
            <div className="rounded-lg overflow-hidden shadow-lg bg-black w-full max-w-[500px] aspect-video">
              {stream && (
                <video
                  playsInline
                  muted
                  ref={myVideo}
                  autoPlay
                  className="w-full h-full object-cover"
                />
              )}
            </div>
            <div className="rounded-lg overflow-hidden shadow-lg bg-gray-800 w-full max-w-[500px] aspect-video">
              {callAccepted && !callEnded ? (
                <video
                  playsInline
                  ref={userVideo}
                  autoPlay
                  className="w-full h-full object-cover"
                />
              ) : (
                <p className="text-gray-400 text-center p-4">No active video</p>
              )}
            </div>
          </div>

          {/* Controls Section */}
          <div className="bg-white p-6 rounded-lg shadow-lg space-y-6">
            <div className="space-y-4">
              <input
                id="filled-basic"
                placeholder="Enter your name"
                value={name}
                onChange={(e) => setName(e.target.value)}
                className="w-full p-3 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
              />
              <span
                onClick={() => {
                  navigator.clipboard.writeText(me);
                }}
                className="material-symbols-rounded text-2xl cursor-pointer text-blue-600 hover:text-blue-800"
              >
                link
              </span>
            </div>

            <div className="space-y-4">
              <input
                id="filled-basic"
                placeholder="Enter ID to call"
                value={idToCall}
                onChange={(e) => setIdToCall(e.target.value)}
                className="w-full p-3 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
              />
              <div className="flex justify-between items-center space-x-4">
                {callAccepted && !callEnded ? (
                  <button
                    onClick={leaveCall}
                    className="px-6 py-2 bg-red-500 text-white font-bold rounded-lg hover:bg-red-600"
                  >
                    End Call
                  </button>
                ) : (
                  <span
                    className="material-symbols-rounded text-4xl text-green-500 cursor-pointer hover:text-green-600"
                    onClick={() => callUser(idToCall)}
                  >
                    call
                  </span>
                )}
                <span className="text-gray-500">{idToCall}</span>
              </div>
            </div>

            {receivingCall && !callAccepted ? (
              <div className="p-4 bg-blue-50 rounded-lg border border-blue-200">
                <h1 className="text-lg font-medium text-blue-800">
                  {name} is calling...
                </h1>
                <button
                  onClick={answerCall}
                  className="mt-4 px-4 py-2 bg-blue-500 text-white rounded-lg hover:bg-blue-600"
                >
                  Answer
                </button>
              </div>
            ) : null}
          </div>
        </div>
      </div>
    </>
  );
}

export default App;
