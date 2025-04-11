package fachada;
import operadores.CodificadorVideo;
import operadores.CompressorVideo;
import operadores.LeitorVideo;

public class VideoFachada {
    private LeitorVideo leitor;
    private CodificadorVideo codificador;
    private CompressorVideo compressor;

    public VideoFachada() {
        leitor = new LeitorVideo();
        codificador = new CodificadorVideo();
        compressor = new CompressorVideo();
    }

    // Método que simplifica a conversão de vídeo
    public void converterVideo(String arquivoOrigem, String formatoDestino) {
        
        // Gerar o nome do arquivo de destino com base no formato
        String arquivoDestino = arquivoOrigem.substring(0, arquivoOrigem.lastIndexOf(".")) + "_convertido." + formatoDestino;
        
        // Chama o codificador para realizar a conversão
        codificador.converter(arquivoOrigem, arquivoDestino);
    }
    public void comprimirVideo(String arquivoOrigem, String arquivoDestino) {
        compressor.comprimir(arquivoOrigem, arquivoDestino);
    }
    public String obterMetadados(String caminhoArquivo) {
    	return leitor.obterMetadados(caminhoArquivo);
    }
}
