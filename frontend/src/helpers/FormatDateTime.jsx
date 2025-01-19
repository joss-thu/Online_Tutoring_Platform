import { format } from "date-fns";

function FormatDateTime(dateString) {
  const date = new Date(dateString);
  return format(date, "dd MMM • HH:mm"); // Example: "20 Jan, 13:00"
}

export default FormatDateTime;
