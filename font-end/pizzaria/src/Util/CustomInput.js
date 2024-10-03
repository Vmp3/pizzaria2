import React from 'react';
import './CustomStyles.css';

const CustomInput = (props) => {
  const { type, name, placeholder, value, onChange, required, disabled, styleType } = props;

  // Determina a classe CSS baseada no estilo do input
  const inputClassName = `custom-input ${styleType}`;

  // Define o estilo do input com base nas props recebidas
  const inputStyle = {
    backgroundColor: disabled ? '#f0f0f0' : 'white', // Cor de fundo cinza para campos desabilitados
  };

  return (
    <input
      type={type}
      name={name}
      placeholder={placeholder}
      value={value}
      onChange={onChange}
      className={inputClassName}
      style={inputStyle}
      required={required}
      disabled={disabled} // Propriedade para desabilitar o input
    />
  );
};

export default CustomInput;
