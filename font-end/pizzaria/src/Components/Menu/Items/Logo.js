import React from 'react';
import { Link } from 'react-router-dom';
import logoPizza from './pizza_logo.png';

const Logo = () => {
  return (
    <div className="header-logo">
      <Link to="/home">
        <img src={logoPizza} alt="Logotipo Senac" />
      </Link>
    </div>
  );
};

export default Logo;
