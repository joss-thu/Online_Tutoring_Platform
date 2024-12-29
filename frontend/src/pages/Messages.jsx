import React, { useEffect, useState } from "react";
import NavBar from "../components/Navbar";
import ChatHistoryItem from "../components/ChatHistoryItem";
import io from "socket.io-client";
import { getUserFromToken } from "../services/AuthService";
import MessageItem from "../components/MessageItem";

const socket = io("http://localhost:5000");

function Messages() {
  const [chats, setChats] = useState([]);
  const [selectedChatId, setSelectedChatId] = useState("");
  const [selectedChatObject, setSelectedChatObject] = useState(null);
  const [ongoingCall, setOngoingCall] = useState(null); // Holds call data (type, user)
  const [userStatus, setUserStatus] = useState({}); // Tracks online status of users
  const [searchQuery, setSearchQuery] = useState("");
  const [messages, setMessages] = useState({});
  const [typedMessage, setTypedMessage] = useState("");
  const [currentUserId, setCurrentUserId] = useState();
  const [rows, setRows] = useState(1);
  const maxRows = 5;

  const readChat = (chatId) => {
    setChats((prevChats) =>
      prevChats.map((chat) =>
        chat.chatId === chatId
          ? { ...chat, unreadMessages: 0, read: true }
          : chat,
      ),
    );
  };

  useEffect(() => {
    // Load messages initially
    loadChats();
    loadMessages();
    const user = getUserFromToken();
    setCurrentUserId(user.id);

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
  const filteredChats = chats.filter((chat) => {
    const { firstName, lastName } = chat.receiver;
    const fullName = `${firstName} ${lastName}`.toLowerCase();
    return fullName.includes(searchQuery.toLowerCase());
  });

  const loadMessages = () => {
    setMessages({
      1: [
        {
          senderId: 2,
          content: "Hi!",
          date: "Sat Dec 28 2024 14:40:27 GMT+0100 (Central European Standard Time)",
        },
        {
          senderId: 1,
          content: "Heyy",
          date: "Sat Dec 28 2024 14:41:27 GMT+0100 (Central European Standard Time)",
        },
        {
          senderId: 2,
          content: "What's up",
          date: "Sat Dec 28 2024 14:42:27 GMT+0100 (Central European Standard Time)",
        },
        {
          senderId: 1,
          content: "Just chilling, wbu?",
          date: "Sat Dec 28 2024 14:43:27 GMT+0100 (Central European Standard Time)",
        },
      ],
      2: [
        {
          senderId: 2,
          content: "Hello!",
          date: "Sat Dec 28 2024 14:33:27 GMT+0100 (Central European Standard Time)",
        },
        {
          senderId: 1,
          content: "Hi",
          date: "Sat Dec 28 2024 14:34:27 GMT+0100 (Central European Standard Time)",
        },
        {
          senderId: 2,
          content: "What's up",
          date: "Sat Dec 28 2024 14:36:27 GMT+0100 (Central European Standard Time)",
        },
        {
          senderId: 1,
          content: "Just chilling, wbu?",
          date: "Sat Dec 28 2024 14:40:27 GMT+0100 (Central European Standard Time)",
        },
      ],
    });
  };

  const loadChats = () => {
    setChats([
      {
        receiver: {
          firstName: "John",
          lastName: "Doe",
        },
        senderId: "2",
        chatId: 1,
        unreadMessages: 1,
        read: true,
      },
      {
        receiver: {
          firstName: "Manav",
          lastName: "Dave",
        },
        chatId: 2,
        senderId: "1",
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

  const handleSendMessage = () => {
    if (typedMessage.trim() === "") return;

    setMessages((prevMessages) => ({
      ...prevMessages,
      [selectedChatId]: [
        ...(prevMessages[selectedChatId] || []),
        { senderId: currentUserId, content: typedMessage, date: new Date() },
      ],
    }));
    setTypedMessage(""); // Clear the input field after sending
    setRows(1);
  };

  const handleKeyPress = (e) => {
    if (e.key === "Enter" && !e.shiftKey) {
      e.preventDefault(); // Prevent the default behavior (creating a new line)
      handleSendMessage(); // Send the message when Enter is pressed
    }
  };

  const handleInputChange = (e) => {
    setTypedMessage(e.target.value);

    // Calculate the new number of rows based on the content
    const lineCount = e.target.value.split("\n").length;
    setRows(Math.min(lineCount, maxRows)); // Limit the rows to maxRows
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
            {filteredChats.map((chat, index) => (
              <ChatHistoryItem
                key={index}
                chat={chat}
                messages={messages[chat.chatId]}
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
                        chats.find((c) => c.chatId === selectedChatId)
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
                        chats.find((c) => c.chatId === selectedChatId)
                          ?.senderId,
                      )
                    }
                  >
                    <span className="material-symbols-rounded">videocam</span>
                  </button>
                </div>
              </header>
              <div className="flex-1 p-4 bg-gray-50 overflow-y-auto flex flex-col space-y-2">
                {messages[selectedChatId] ? (
                  messages[selectedChatId].map((message, index) => (
                    <MessageItem
                      key={index}
                      message={message}
                      currentUserId={currentUserId}
                    />
                  ))
                ) : (
                  <p className="text-gray-500 text-center">No messages yet</p>
                )}
              </div>

              <footer className="p-4 bg-white flex items-center space-x-3">
                <textarea
                  rows={rows}
                  placeholder="Your message"
                  value={typedMessage}
                  onKeyDown={handleKeyPress}
                  onChange={handleInputChange}
                  className="flex-1 px-4 py-2 bg-gray-200 rounded-xl text-sm text-gray-800 outline-none resize-none"
                />
                <button
                  className="bg-blue-900 text-white px-4 py-2 rounded-full"
                  onClick={handleSendMessage}
                >
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
