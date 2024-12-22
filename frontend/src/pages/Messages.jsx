import React, { useEffect } from "react";
import NavBar from "../components/Navbar";
import ChatHistoryItem from "../components/ChatHistoryItem";

function Messages() {
  const [messages, setMessages] = React.useState([]);
  const [selectedChat, setSelectedChat] = React.useState("");

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
          firstName: "Jane",
          lastName: "Doe",
        },
        chatId: "2",
        senderId: "1",
        lastMessage: "Hello",
        lastMessageDate: "2024-11-27",
        lastMessageSenderId: "1",
        unreadMessages: 3,
        read: false,
      },
    ]);
  };

  useEffect(() => {
    loadMessages();
  }, []);

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
                className="w-full px-10 py-4 rounded-2xl bg-gray-100 text-sm text-gray-600 outline-none"
              />
            </div>
          </div>
          <div className="overflow-y-auto">
            {messages.map((message, index) => (
              <ChatHistoryItem
                key={index}
                message={message}
                selectedChat={selectedChat}
                setSelectedChat={setSelectedChat}
              />
            ))}
          </div>
        </div>

        {/* Chat Section */}
        {selectedChat !== "" ? (
          <>
            <div className="flex-1 flex flex-col">
              <header className="p-4 bg-white shadow-sm flex items-center justify-between">
                <h2 className="text-gray-800 font-medium">Chat</h2>
                <div className="flex items-center space-x-4">
                  <button className="text-gray-600 hover:text-gray-800">
                    <i className="fas fa-phone"></i>
                  </button>
                  <button className="text-gray-600 hover:text-gray-800">
                    <i className="fas fa-video"></i>
                  </button>
                  <button className="text-gray-600 hover:text-gray-800">
                    <i className="fas fa-ellipsis-v"></i>
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
                  className="flex-1 px-4 py-2 bg-gray-100 rounded-full text-sm text-gray-600 outline-none"
                />
                <button className="bg-blue-500 text-white px-4 py-2 rounded-full">
                  Send
                </button>
              </footer>
            </div>

            {/* Info Panel */}
            <div className="w-1/4 bg-white border-l border-gray-200 p-4">
              <h3 className="text-gray-800 font-medium">Chat Info</h3>
              {/* Additional info like members, shared files, etc. */}
            </div>
          </>
        ) : (
          <div className="flex-1 flex flex-col bg-white border-l border-gray-200 p-4 justify-center items-center">
            <h3 className="text-gray-800 font-medium">Select a Chat</h3>
          </div>
        )}
      </div>
    </div>
  );
}

export default Messages;
