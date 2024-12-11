import React from "react";

function ChatHistoryItem({ message, selectedChat, setSelectedChat }) {
  const {
    receiver,
    lastMessage,
    lastMessageDate,
    lastMessageSenderId,
    senderId,
    unreadMessages,
    chatId,
  } = message;

  const isLastMessageSentByUser = lastMessageSenderId === senderId;

  const getInitials = (name) =>
    name
      .split(" ")
      .map((part) => part.charAt(0))
      .join("")
      .toUpperCase();

  const receiverInitials = getInitials(
    `${receiver.firstName} ${receiver.lastName}`,
  );

  return (
    <div
      onClick={() => setSelectedChat(chatId)}
      className={`flex items-center p-3 border-b border-gray-200 cursor-pointer transition-all ${
        selectedChat === chatId ? "bg-gray-100" : "bg-white"
      } hover:bg-gray-100`}
    >
      {/* Profile Picture */}
      <div className="relative w-12 h-12 bg-gray-200 rounded-full flex items-center justify-center font-bold text-gray-600">
        {receiver.profilePicture ? (
          <img
            src={receiver.profilePicture}
            alt={`${receiver.firstName} ${receiver.lastName}`}
            className="w-full h-full rounded-full object-cover"
          />
        ) : (
          receiverInitials
        )}
      </div>

      {/* Message Details */}
      <div className="flex flex-col ml-3 flex-1">
        <div className="flex justify-between items-center">
          <h4 className="text-sm font-medium text-gray-800 truncate">
            {`${receiver.firstName} ${receiver.lastName}`}
          </h4>
          <span className="text-xs text-gray-500">
            {new Date(lastMessageDate).toLocaleTimeString([], {
              hour: "2-digit",
              minute: "2-digit",
            })}
          </span>
        </div>
        <p
          className={`text-xs truncate ${
            isLastMessageSentByUser ? "text-gray-400 italic" : "text-gray-600"
          }`}
        >
          {isLastMessageSentByUser ? `You: ${lastMessage}` : lastMessage}
        </p>
      </div>

      {/* Unread Badge */}
      {!isLastMessageSentByUser && unreadMessages > 0 && (
        <div className="ml-2 w-6 h-6 flex items-center justify-center bg-blue-500 text-white text-xs font-bold rounded-full">
          {unreadMessages}
        </div>
      )}
    </div>
  );
}

export default ChatHistoryItem;
