import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";
import "./Login.css";
import { formatCPF } from "../../Util/Utils";
import CustomInput from "../../Util/CustomInput";
import CustomButton from "../../Util/CustomButton";

function Login() {
  const [cpf, setCpf] = useState("");
  const [senha, setSenha] = useState("");
  const [mensagem, setMensagem] = useState("");
  const navigate = useNavigate();

  const handleCpfChange = (event) => {
    const formattedCpf = formatCPF(event.target.value);
    setCpf(formattedCpf);
  };

  const handleSubmit = async (event) => {
    event.preventDefault();
    try {
      const response = await axios.post(apiURL + "/login", {
        cpf,
        senha,
      });

      if (response.status === 200) {
        const userId = response.data;
        localStorage.setItem('userId', userId);
        setMensagem("Login realizado com sucesso!");
        navigate("/home");
      } else {
        setMensagem("Erro ao fazer login.");
      }
    } catch (error) {
      console.error("Erro ao fazer login:", error);
      if (error.response) {
        if (error.response.status === 401) {
          setMensagem("Credenciais inválidas.");
        } else {
          setMensagem("Erro ao fazer login: " + error.response.data);
        }
      } else {
        setMensagem("Erro ao fazer login: " + error.message);
      }
    }
  };

  return (
    <div className="login-container">
      <h2 className="login-title">Login</h2>
      <form onSubmit={handleSubmit} className="login-form">
        <CustomInput
          type="text"
          placeholder="CPF"
          value={cpf}
          onChange={handleCpfChange}
          required
        />
        <CustomInput
          type="password"
          placeholder="Senha"
          value={senha}
          onChange={(e) => setSenha(e.target.value)}
          required
        />
        <CustomButton text="Login" />
      </form>
      <p className="mensagem">{mensagem}</p>
    </div>
  );
}

export default Login;
