import React from 'react';
import './CustomStyles.css';

const CustomButton = (props) => {
  const { text, onClick, styleType, backgroundColor } = props;

  const buttonStyle = {
    backgroundColor: backgroundColor || '#FFA500',
    cursor: 'pointer',
  };

  const buttonClassName = `custom-button ${styleType}`;

  return (
    <button style={buttonStyle} className={buttonClassName} onClick={onClick}>
      {text}
    </button>
  );
};

export default CustomButton;
