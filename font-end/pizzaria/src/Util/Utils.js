export const formatCPF = (value) => {
    let cpfFormatted = value.replace(/\D/g, "");
    cpfFormatted = cpfFormatted.substring(0, 11);
    cpfFormatted = cpfFormatted.replace(/(\d{3})(\d)/, "$1.$2");
    cpfFormatted = cpfFormatted.replace(/(\d{3})(\d)/, "$1.$2");
    cpfFormatted = cpfFormatted.replace(/(\d{3})(\d{1,2})$/, "$1-$2");
    return cpfFormatted;
  };
  
  export const formatCEP = (value) => {
    let cepFormatted = value.replace(/\D/g, "");
    cepFormatted = cepFormatted.substring(0, 8);
    cepFormatted = cepFormatted.replace(/(\d{5})(\d)/, "$1-$2");
    return cepFormatted;
  };
  