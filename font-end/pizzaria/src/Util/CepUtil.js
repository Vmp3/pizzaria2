import axios from 'axios';
import { formatCEP } from './Utils';

export const handleCepChange = async (event, setCep, setEndereco) => {
    const newCep = formatCEP(event.target.value);
    setCep(newCep);

    if (newCep.length === 9) {
        try {
            const response = await axios.get(`https://viacep.com.br/ws/${newCep.replace("-", "")}/json/`);
            const data = response.data;
            if (!data.erro) {
                setEndereco(data.logradouro);
            } else {
                setEndereco("");
                alert("CEP inválido");
            }
        } catch (error) {
            console.error("Erro ao buscar CEP:", error);
        }
    }
};
