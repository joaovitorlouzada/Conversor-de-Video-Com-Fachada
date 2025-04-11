package operadores;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class CompressorVideo {
    public void comprimir(String arquivoOrigem, String arquivoDestino) {
        // Caminho para o FFmpeg (pode ser configurado ou alterado conforme necessário)
        String caminhoFFmpeg = "C:\\Users\\joaov\\Downloads\\ffmpeg-2024-11-13-git-322b240cea-full_build\\ffmpeg-2024-11-13-git-322b240cea-full_build\\bin\\ffmpeg.exe";

        // Comando de compressão, similar ao comando de conversão
        String comandoCompressao = "\"" + caminhoFFmpeg + "\" -i \"" + arquivoOrigem + "\" -b:v 500k -bufsize 500k \"" + arquivoDestino + "\"";

        // Execução do processo FFmpeg
        try {
            Process processo = Runtime.getRuntime().exec(comandoCompressao);
            
            InputStream erroStream = processo.getErrorStream();
            BufferedReader readerErro = new BufferedReader(new InputStreamReader(erroStream));
            String linha;
            while ((linha = readerErro.readLine()) != null) {
                System.out.println("Erro FFmpeg: " + linha);  // Imprime os erros do FFmpeg
            }
            
            int resultado = processo.waitFor(); // Aguardar o término do processo
            if (resultado == 0) {
                System.out.println("Vídeo comprimido com sucesso para: " + arquivoDestino);
            } else {
                System.out.println("Erro na compressão do vídeo.");
            }
        } catch (IOException | InterruptedException e) {
            // Em caso de erro, a exceção é capturada e a mensagem é exibida
            System.out.println("Erro ao executar a compressão: " + e.getMessage());
        }
    }
}
