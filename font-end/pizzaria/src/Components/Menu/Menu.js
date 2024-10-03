import React from 'react';
import './Menu.css';
import Logo from './Items/Logo';
import Title from './Items/Title';
import NavigationLinks from './Items/NavigationLinks';

const Menu = () => {
  return (
    <div className="header-container">
      <header className="header-bottom">
        <div className="header-left">
          <Logo />
          <Title />
        </div>
        <div className="header-right">
          <NavigationLinks />
        </div>
      </header>
    </div>
  );
};

export default Menu;
