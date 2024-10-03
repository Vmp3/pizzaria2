import React from "react";
import { NavLink as RouterNavLink } from "react-router-dom";

const NavLink = ({ to, className, children, onClick }) => (
  <RouterNavLink
    to={to}
    className={({ isActive }) => (isActive ? `${className} active` : className)}
    onClick={onClick}
  >
    {children}
  </RouterNavLink>
);

export default NavLink;
