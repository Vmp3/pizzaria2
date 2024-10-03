import React from "react";
import "./Sobre.css";

const Sobre = () => {
  return (
    <div id="sobre" className="about-container">
      <div className="image-container">
        <img src="/Util/SobreImage.jpg" alt="Water" className="image-about" />
      </div>
      <div className="text-container">
        <h2>Sobre nós</h2>
        <p>
          Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed ut neque
          ac odio tincidunt vestibulum. Nam faucibus justo a orci faucibus
          consectetur. Quisque eget justo at dui scelerisque porta a nec arcu.
          Duis sit amet lorem nec lectus vehicula venenatis vel id tortor.
          Nullam in ex vitae odio fermentum interdum. Ut auctor ipsum a nisi
          finibus, in eleifend nisl aliquam. Vestibulum laoreet ligula quis nisi
          suscipit, nec fermentum quam vehicula. Phasellus vel tempor elit. Cras
          ac magna id ligula facilisis tincidunt vel in ligula.
        </p>
      </div>
    </div>
  );
};

export default Sobre;
