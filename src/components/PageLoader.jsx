import { Component } from "react";

import ComponentStyles from "../../styles/Component.module.css";

export default class PageLoader extends Component {
  render() {
    const { color, progress, transition } = this.props;
    return (
      <>
        <span
          className={ComponentStyles.absolute}
          style={{
            zIndex: 50000,
            width: `${progress}%`,
            height: "3px",
            backgroundColor: color,
            boxShadow: `0 0 6px ${color}`,
            transition: `${transition}s all ease`,
          }}
        ></span>
      </>
    );
  }
}
