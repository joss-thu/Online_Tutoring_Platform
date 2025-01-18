import { useSocket } from "../services/SocketContext";

const CallNotification = () => {
  const { incomingCall, acceptCall, rejectCall } = useSocket();

  if (!incomingCall) return null;

  return (
    <div className="fixed bottom-5 right-5 bg-gray-900 text-white p-10 rounded-lg shadow-lg font-merriweather_sans">
      <p className="font-bold">Incoming Call</p>
      <div className="flex space-x-4 mt-2">
        <button
          onClick={acceptCall}
          className="bg-green-500 px-4 py-2 rounded hover:bg-green-600"
        >
          Accept
        </button>
        <button
          onClick={rejectCall}
          className="bg-red-500 px-4 py-2 rounded hover:bg-red-600"
        >
          Reject
        </button>
      </div>
    </div>
  );
};

export default CallNotification;
