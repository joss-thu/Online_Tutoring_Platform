import { useSocket } from "../services/SocketContext";
import ActionButton from "./ActionButton";

const CallNotification = () => {
  const { incomingCall, incomingUserName, acceptCall, rejectCall } =
    useSocket();

  if (!incomingCall) return null;

  return (
    <div className="fixed bottom-5 right-5 bg-gray-900 text-white p-6 rounded-3xl shadow-lg font-merriweather_sans">
      <div className="flex space-x-2">
        <span className="text-xl text-white material-symbols-rounded">
          call
        </span>
        <p className="text-lg text-center w-full">
          Incoming Call from{" "}
          <i>
            <strong>{incomingUserName}</strong>
          </i>
        </p>
      </div>

      <div className="flex space-x-4 mt-4">
        <ActionButton
          onClick={acceptCall}
          text={"Accept"}
          design={"positive"}
        />
        <ActionButton onClick={rejectCall} text={"Reject"} design={"alert"} />
      </div>
    </div>
  );
};

export default CallNotification;
