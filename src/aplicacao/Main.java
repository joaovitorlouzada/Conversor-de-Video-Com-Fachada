package aplicacao;
import javax.swing.*;

import fachada.VideoFachada;

import java.io.File;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Criar a fachada para realizar a conversão e compressão
        VideoFachada fachada = new VideoFachada();

        // Usar JFileChooser para permitir que o usuário selecione um arquivo
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Selecione o arquivo de vídeo");
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Vídeos MP4, AVI, MKV", "mp4", "avi", "mkv"));

        int resultado = fileChooser.showOpenDialog(null);

        if (resultado == JFileChooser.APPROVE_OPTION) {
            // Obter o arquivo selecionado
            File arquivoSelecionado = fileChooser.getSelectedFile();
            String caminhoArquivo = arquivoSelecionado.getAbsolutePath();

            // Exibir o nome do arquivo selecionado
            System.out.println("Você selecionou o arquivo: " + caminhoArquivo);

            // Criar um Scanner para capturar a entrada do usuário
            Scanner scanner = new Scanner(System.in);
            int escolha;

            // Menu que ficará sendo exibido até o usuário selecionar a opção de saída (4)
            do {
                System.out.println("\nEscolha a operação desejada:");
                System.out.println("1. Obter metadados do vídeo");
                System.out.println("2. Converter vídeo");
                System.out.println("3. Comprimir vídeo");
                System.out.println("4. Sair");
                System.out.print("Digite o número da operação (1, 2, 3 ou 4): ");
                escolha = scanner.nextInt();

                // Realizar a operação com base na escolha do usuário
                switch (escolha) {
                    case 1:
                        String metadados = fachada.obterMetadados(caminhoArquivo);
                        System.out.println("Metadados do vídeo:\n" + metadados);
                        break;
                    case 2:
                        // Pedir o formato de destino para conversão
                        System.out.print("Digite o formato de destino (ex: mp4, mkv, avi): ");
                        String formatoDestino = scanner.next();
                        // Chamar a fachada para converter o vídeo
                        fachada.converterVideo(caminhoArquivo, formatoDestino);
                        break;
                    case 3:
                        // Gerar o nome do arquivo comprimido com base no nome do arquivo original
                        String arquivoDestino = caminhoArquivo.substring(0, caminhoArquivo.lastIndexOf(".")) + "_comprimido" + caminhoArquivo.substring(caminhoArquivo.lastIndexOf("."));
                        // Chamar a fachada para comprimir o vídeo
                        fachada.comprimirVideo(caminhoArquivo, arquivoDestino);
                        break;
                    case 4:
                        System.out.println("Saindo do programa.");
                        break;
                    default:
                        System.out.println("Opção inválida. Por favor, escolha uma opção válida.");
                        break;
                }
            } while (escolha != 4); // O loop continua até o usuário escolher a opção 4 para sair
        } else {
            System.out.println("Nenhum arquivo selecionado.");
        }
    }
}
