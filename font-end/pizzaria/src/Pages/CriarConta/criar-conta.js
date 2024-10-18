import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";
import "./criar-conta.css";
import { formatCPF } from "../../Util/Utils";
import { handleCepChange } from "../../Util/CepUtil";
import CustomInput from "../../Util/CustomInput";
import CustomButton from "../../Util/CustomButton";
const apiURL = process.env.REACT_APP_API_URL;
function CriarConta() {
    const [cpf, setCpf] = useState("");
    const [nome, setNome] = useState("");
    const [endereco, setEndereco] = useState("");
    const [numero, setNumero] = useState("");
    const [email, setEmail] = useState("");
    const [senha, setSenha] = useState("");
    const [cep, setCep] = useState("");
    const navigate = useNavigate();

    const handleCpfChange = (event) => {
        const formattedCpf = formatCPF(event.target.value);
        setCpf(formattedCpf);
    };

    const handleSubmit = async (event) => {
        event.preventDefault();
       
        const trimmedNome = nome.trim();
        const trimmedNumero = numero.trim();
        const trimmedSenha = senha.trim();
 
        if (trimmedNome === "" || trimmedNumero === "" || trimmedSenha === "") {
            alert("Por favor, preencha todos os campos corretamente.");
            return;
        }
 
        try {
            const response = await axios.post(apiURL + "/criar-conta", {
                cpf,
                nome: trimmedNome,
                cep,
                endereco,
                numero: trimmedNumero,
                email,
                senha: trimmedSenha,
            });
            console.log(response.data);
            alert("Conta criada com sucesso!");
            navigate("/login"); 
        } catch (error) {
            console.error("Erro ao criar conta:", error);
            if (error.response && error.response.data) {
                alert("Erro ao criar conta: " + error.response.data);
            } else {
                alert("Erro ao criar conta: " + error.message);
            }
        }
    };

    const handleLoginRedirect = () => {
        navigate("/login");
    };

    return (
        <div className="criar-conta-container">
            <h2>Criar Conta</h2>
            <form onSubmit={handleSubmit} className="criar-conta-form">
                <CustomInput
                    type="text"
                    placeholder="CPF"
                    value={cpf}
                    onChange={handleCpfChange}
                    required
                />
                <CustomInput
                    type="text"
                    placeholder="Nome"
                    value={nome}
                    onChange={(e) => setNome(e.target.value)}
                    required
                />
                <CustomInput
                    type="text"
                    placeholder="CEP"
                    value={cep}
                    onChange={(e) => handleCepChange(e, setCep, setEndereco)}
                    maxLength={9}
                    required
                />
                <CustomInput
                    type="text"
                    placeholder="Endereço"
                    value={endereco}
                    onChange={(e) => setEndereco(e.target.value)}
                    readOnly
                    required
                />
                <CustomInput
                    type="text"
                    placeholder="Número"
                    value={numero}
                    onChange={(e) => setNumero(e.target.value)}
                    required
                />
                <CustomInput
                    type="email"
                    placeholder="Email"
                    value={email}
                    onChange={(e) => setEmail(e.target.value)}
                    required
                />
                <CustomInput
                    type="password"
                    placeholder="Senha"
                    value={senha}
                    onChange={(e) => setSenha(e.target.value)}
                    required
                />
                <CustomButton type="submit" text="Criar Conta" className="custom-button" />
            </form>
            <CustomButton type="button" text="Já tem uma conta? Faça seu login" onClick={handleLoginRedirect} className="custom-button" />
        </div>
    );
}

export default CriarConta;
