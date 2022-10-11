package com.itau.ioapi;

import com.itau.ioapi.model.Pessoa;
import com.itau.ioapi.service.PessoaFileSystemService;
import com.itau.ioapi.service.SignosService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@SpringBootApplication
@RequiredArgsConstructor
public class IoapiApplication implements CommandLineRunner {

    private final PessoaFileSystemService pessoaFileSystemService;
    private final SignosService signosService;

    private static final String HOME_DIR = System.getProperty("user.dir");

    public static void main(String[] args) {
        SpringApplication.run(IoapiApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Path path = Paths.get(HOME_DIR, "/mapas/mapa-virgulas.txt");

        // Desserializa o arquivo
        List<Pessoa> pessoas = pessoaFileSystemService.desserialize(path);

        // Imprime as informaçoes no console com multiplas threads (muda a ordem nas execuçoes)
        pessoas.stream().parallel().forEach(signosService::imprimirInformacoesSignos);

//        Executor executors = Executors.newFixedThreadPool(20);
//
//        final List<CompletableFuture<List<String>>> pessoaFuture = pessoas
//                .stream()
//                .map(p -> {
//                    System.out.println("Passando no Map -> Thread: " + Thread.currentThread().getName());
//                    return
//                        CompletableFuture
//                                .supplyAsync(() -> signosService.getInformacoesSignosEmString(p), executors);
//                        }
//                )
//                .collect(Collectors.toList());
//
//
//        List<List<String>> pessoasList = pessoaFuture
//                .stream()
//                .map(CompletableFuture::join)
//                .collect(Collectors.toList());
//
//        pessoasList.forEach(getConsumerForSerialize());

        pessoas.stream()
                .parallel()
                .map(signosService::getInformacoesSignosEmString)
                .forEach(getConsumerForSerialize());

    }
//

    private Consumer<List<String>> getConsumerForSerialize() {
        return stringList -> {
            System.out.println("Gravacao de Arquivos: " + Thread.currentThread().getName());
            Path newPathForPessoa = Paths.get(HOME_DIR, "mapas", "quantico", stringList.get(0) + ".txt");
            try {
                pessoaFileSystemService.serialize(stringList, newPathForPessoa);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        };
    }
}
