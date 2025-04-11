package operadores;
import java.io.*;

public class CodificadorVideo {
    public void converter(String arquivoOrigem, String arquivoDestino) {
        // Caminho do FFmpeg
        String caminhoFFmpeg = "C:\\Users\\joaov\\Downloads\\ffmpeg-2024-11-13-git-322b240cea-full_build\\ffmpeg-2024-11-13-git-322b240cea-full_build\\bin\\ffmpeg.exe";
        
        // Comando FFmpeg para converter o vídeo, com as aspas corretamente posicionadas
        String comando = "\"" + caminhoFFmpeg + "\" -i \"" + arquivoOrigem + "\" -c:v libx264 -c:a aac -strict experimental \"" + arquivoDestino + "\"";
        
        try {
            // Executa o comando do FFmpeg
            Process processo = Runtime.getRuntime().exec(comando);
            
            // Captura a saída de erro do FFmpeg
            InputStream erroStream = processo.getErrorStream();
            BufferedReader readerErro = new BufferedReader(new InputStreamReader(erroStream));
            String linha;
            while ((linha = readerErro.readLine()) != null) {
                System.out.println("Erro FFmpeg: " + linha);  // Imprime os erros do FFmpeg
            }

            // Aguarda o processo terminar
            int resultado = processo.waitFor();
            if (resultado == 0) {
                System.out.println("Conversão bem-sucedida para: " + arquivoDestino);
            } else {
                System.out.println("Erro na conversão do vídeo.");
            }
        } catch (IOException | InterruptedException e) {
            System.out.println("Erro ao executar o comando do FFmpeg: " + e.getMessage());
        }
    }
}
