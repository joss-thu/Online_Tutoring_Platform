import { render, screen } from "@testing-library/react";
import ActionButton from "../components/ActionButton";

test("renders correctly", async () => {
  const buttonText = "Hello World";
  render(<ActionButton text={buttonText} />);
  const element = screen.getByText(/Hello World/i);
  expect(element).toBeInTheDocument();
});
