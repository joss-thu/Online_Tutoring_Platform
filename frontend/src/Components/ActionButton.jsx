export default function ActionButton(props) {
  return (
    <button className="btn btn-primary" onClick={props.onClick}>
      {props.text}
    </button>
  );
}
