package operadores;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class LeitorVideo {

    public String obterMetadados(String caminhoArquivo) {
        String caminhoFFmpeg = "C:\\Users\\joaov\\Downloads\\ffmpeg-2024-11-13-git-322b240cea-full_build\\ffmpeg-2024-11-13-git-322b240cea-full_build\\bin\\ffmpeg.exe";
        String comando = "\"" + caminhoFFmpeg + "\" -i \"" + caminhoArquivo + "\" 2>&1"; // 2>&1 para capturar os erros (os metadados)

        try {
            Process processo = Runtime.getRuntime().exec(comando);
            BufferedReader reader = new BufferedReader(new InputStreamReader(processo.getErrorStream()));
            StringBuilder metadados = new StringBuilder();
            String linha;

            // Variáveis para armazenar as informações relevantes
            String formato = "Formato não encontrado";
            String resolucao = "Resolução não encontrada";
            String duracao = "Duração não encontrada";
            String taxaBits = "Taxa de bits não encontrada";

            // Filtra as informações relevantes
            while ((linha = reader.readLine()) != null) {

                // Procurar o formato do arquivo (linha de entrada)
                if (linha.contains("Input #0")) {
                    // Exemplo de linha: "Input #0, mov,mp4,m4a,3gp,3g2,mj2, from 'video.mp4':"
                    int index = linha.indexOf("from");
                    if (index != -1) {
                        formato = linha.substring(linha.indexOf(",") + 1, index).trim(); // Pega o formato (ex: "mp4")
                    }
                }

                // Procurar a resolução (largura x altura)
                if (linha.contains("Video:")) {
                    // Exemplo: "Video: h264 (High) (avc1 / 0x31637661), yuv420p(tv, smpte170m/bt470bg/smpte170m, progressive), 720x1280, 297 kb/s, 30 fps"
                    int indexRes = linha.indexOf("x");
                    if (indexRes != -1) {
                        // Tentamos pegar o valor no formato largura x altura
                        String resolucaoPossivel = linha.substring(indexRes - 3, indexRes + 5); // Exemplo de "720x1280"
                        if (resolucaoPossivel.matches("\\d+x\\d+")) {  // Verifica se é no formato numérico correto
                            resolucao = resolucaoPossivel;  // Exemplo: "720x1280"
                        }
                    }
                }

                // Procurar a duração
                if (linha.contains("Duration:")) {
                    int index = linha.indexOf("Duration:");
                    if (index != -1) {
                        duracao = linha.substring(index + 9, index + 20).trim(); // Ex: 00:00:14.16
                    }
                }

                // Procurar a taxa de bits
                if (linha.contains("bitrate")) {
                    int index = linha.indexOf("bitrate");
                    if (index != -1) {
                        taxaBits = linha.substring(index + 8).trim(); // Ex: 363 kb/s
                    }
                }
            }

            // Monta uma saída amigável
            metadados.append("Formato: ").append(formato).append("\n");
            metadados.append("Resolução: ").append(resolucao).append("\n");
            metadados.append("Duração: ").append(duracao).append("\n");
            metadados.append("Taxa de Bits: ").append(taxaBits).append("\n");

            processo.waitFor();

            return metadados.toString();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return "Erro ao obter metadados: " + e.getMessage();
        }
    }
}
