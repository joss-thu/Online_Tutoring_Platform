import React, { useEffect, useState } from "react";
import NavBar from "../components/Navbar";
import ChatHistoryItem from "../components/ChatHistoryItem";
import io from "socket.io-client";

const socket = io("http://localhost:5000");

function Messages() {
  const [messages, setMessages] = useState([]);
  const [selectedChatId, setSelectedChatId] = useState("");
  const [selectedChatObject, setSelectedChatObject] = useState(null);
  const [ongoingCall, setOngoingCall] = useState(null); // Holds call data (type, user)
  const [userStatus, setUserStatus] = useState({}); // Tracks online status of users
  const [searchQuery, setSearchQuery] = useState("");

  const readChat = (chatId) => {
    setMessages((prevMessages) =>
      prevMessages.map((message) =>
        message.chatId === chatId
          ? { ...message, unreadMessages: 0, read: true }
          : message,
      ),
    );
  };

  useEffect(() => {
    // Load messages initially
    loadMessages();

    // Listen for user status updates
    socket.on("userStatusUpdate", ({ userId, isOnline }) => {
      setUserStatus((prev) => ({
        ...prev,
        [userId]: isOnline,
      }));
    });

    // Listen for call events (e.g., call ended by the other user)
    socket.on("callEnded", () => {
      setOngoingCall(null);
    });

    return () => {
      socket.disconnect();
    };
  }, []);

  // Function to handle filtering
  const filteredMessages = messages.filter((message) => {
    const { firstName, lastName } = message.receiver;
    const fullName = `${firstName} ${lastName}`.toLowerCase();
    return fullName.includes(searchQuery.toLowerCase());
  });

  const loadMessages = () => {
    setMessages([
      {
        receiver: {
          firstName: "John",
          lastName: "Doe",
        },
        senderId: "2",
        chatId: "1",
        lastMessage: "Hello",
        lastMessageDate: "2024-11-27",
        lastMessageSenderId: "1",
        unreadMessages: 1,
        read: true,
      },
      {
        receiver: {
          firstName: "Manav",
          lastName: "Dave",
        },
        chatId: "2",
        senderId: "1",
        lastMessage: "World",
        lastMessageDate: "2024-11-27",
        lastMessageSenderId: "1",
        unreadMessages: 3,
        read: false,
      },
    ]);
  };

  const startCall = (type, userId) => {
    // Check if the user is online
    if (userStatus[userId]) {
      setOngoingCall({ type, userId });
      // Emit call initiation to the server
      socket.emit("callUser", {
        userToCall: userId,
        from: socket.id, // Your socket ID
        type,
      });
    } else {
      alert("User is offline. You cannot call them.");
    }
  };

  const endCall = () => {
    setOngoingCall(null);
    socket.emit("endCall", { to: ongoingCall.userId });
  };

  return (
    <div className="flex flex-col w-full h-screen overflow-hidden font-merriweather_sans">
      {/* Navbar */}
      <NavBar currentPage={document.location.pathname} />

      <div className="flex flex-1 bg-gray-50" style={{ paddingTop: "70px" }}>
        {/* Sidebar */}
        <div className="w-1/4 bg-white border-r border-gray-200">
          <div className="p-4">
            <div className="relative">
              <span className="absolute left-3 top-3.5 text-gray-500">
                <i className="material-symbols-rounded text-md">search</i>
              </span>
              <input
                type="text"
                placeholder="Search"
                value={searchQuery}
                onChange={(e) => setSearchQuery(e.target.value)}
                className="w-full px-10 py-4 rounded-2xl bg-gray-100 text-sm text-gray-600 outline-none"
              />
            </div>
          </div>
          <div className="overflow-y-auto">
            {filteredMessages.map((message, index) => (
              <ChatHistoryItem
                key={index}
                message={message}
                selectedChatId={selectedChatId}
                setSelectedChatId={setSelectedChatId}
                setSelectedChatObject={setSelectedChatObject}
                readChat={readChat}
              />
            ))}
          </div>
        </div>

        {/* Chat Section */}
        {selectedChatId !== "" ? (
          <>
            <div className="flex-1 flex flex-col">
              <header className="px-4 py-2 bg-blue-900 shadow-sm flex items-center justify-between rounded-2xl m-4">
                <h2 className="text-white">Chat</h2>
                <div
                  className="flex items-center space-x-3
                mt-1.5"
                >
                  <button
                    className="text-white hover:text-gray-300"
                    onClick={() =>
                      startCall(
                        "audio",
                        messages.find((m) => m.chatId === selectedChatId)
                          ?.senderId,
                      )
                    }
                  >
                    <span className="material-symbols-rounded">call</span>
                  </button>
                  <button
                    className="text-white hover:text-gray-300"
                    onClick={() =>
                      startCall(
                        "video",
                        messages.find((m) => m.chatId === selectedChatId)
                          ?.senderId,
                      )
                    }
                  >
                    <span className="material-symbols-rounded">videocam</span>
                  </button>
                </div>
              </header>
              <div className="flex-1 p-4 bg-gray-50 overflow-y-auto">
                {/* Message threads */}
              </div>
              <footer className="p-4 bg-white flex items-center space-x-3">
                <input
                  type="text"
                  placeholder="Your message"
                  className="flex-1 px-4 py-2 bg-gray-200 rounded-full text-sm text-gray-800 outline-none"
                />
                <button className="bg-blue-900 text-white px-4 py-2 rounded-full">
                  Send
                </button>
              </footer>
            </div>

            {/* Info Panel */}
            <div className="w-1/4 bg-white text-xl border-l border-gray-200 p-4">
              {/* Additional info like members, shared files, etc. */}
              {selectedChatObject.receiver.firstName}{" "}
              {selectedChatObject.receiver.lastName}
            </div>
          </>
        ) : (
          <div className="flex-1 flex flex-col bg-white border-l border-gray-200 p-4 justify-center items-center">
            <h3 className="text-gray-800 font-medium">Select a Chat</h3>
          </div>
        )}
      </div>

      {/* Call Interface */}
      {ongoingCall && (
        <div className="fixed inset-0 bg-black bg-opacity-50 flex justify-center items-center">
          <div className="bg-white rounded-lg p-6 shadow-lg w-1/3">
            <h2 className="text-gray-800 font-medium">
              {ongoingCall.type === "audio" ? "Audio Call" : "Video Call"} with{" "}
              {ongoingCall.userId}
            </h2>
            <button
              className="bg-red-500 text-white px-4 py-2 rounded-full mt-4"
              onClick={endCall}
            >
              End Call
            </button>
          </div>
        </div>
      )}
    </div>
  );
}

export default Messages;
