import React from "react";
import "./Footer.css";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faPhone, faClock, faMapMarker} from "@fortawesome/free-solid-svg-icons";
import { faFacebook, faInstagram, faTwitter} from "@fortawesome/free-brands-svg-icons";

const ContactInfo = () => {
  return (
    <div className="footer-contact-info">
      <div className="footer-contact-item">
        <FontAwesomeIcon icon={faMapMarker} />
        <span>Avenida Pequeno Príncipe 1817, 88063-000</span>
      </div>
      <hr className="vertical-line" />
      <div className="footer-contact-item">
        <FontAwesomeIcon icon={faPhone} />
        <span>(48) 3232-2424</span>
      </div>
      <hr className="vertical-line" />
      <div className="footer-contact-item">
        <FontAwesomeIcon icon={faClock} />
        <span>Ter-Dom: 18h - 00h</span>
      </div>
    </div>
  );
};

const SocialMediaLinks = () => {
  return (
    <div className="footer-social-media">
      <a href="https://www.facebook.com">
        <FontAwesomeIcon icon={faFacebook} />
      </a>
      <a href="https://www.instagram.com">
        <FontAwesomeIcon icon={faInstagram} />
      </a>
      <a href="https://www.twitter.com">
        <FontAwesomeIcon icon={faTwitter} />
      </a>
    </div>
  );
};

const Footer = () => {
  return (
    <footer id="contato" className="footer-container">
      <div className="footer-contact">
        <ContactInfo />
        <SocialMediaLinks />
      </div>
    </footer>
  );
};

export default Footer;
