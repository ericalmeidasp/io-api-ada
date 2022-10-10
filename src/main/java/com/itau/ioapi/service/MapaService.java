package com.itau.ioapi.service;

import com.itau.ioapi.model.Pessoa;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Service
@RequiredArgsConstructor
public class MapaService {
    private final SignosService signosService;
    private final PessoaFileSystemService pessoaFileSystemService;
    private static final String HOME_DIR = System.getProperty("user.dir");

    public ByteArrayOutputStream generateOutputStream(MultipartFile file) throws IOException {

        List<Pessoa> pessoaList = generateModelListFromAFile(file);

        List<String> stringPessoasList = transformPessoaListInStringList(pessoaList);

        ByteArrayOutputStream baos = generateOutputStream(stringPessoasList);

        return baos;
    }

    public ByteArrayOutputStream generateOutputStream(List<String> stringList) throws IOException {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ZipOutputStream zos = new ZipOutputStream(baos);
        stringList.forEach(string -> {
            ZipEntry entry = new ZipEntry(string.split("\n")[0] + ".txt");
            try {
                zos.putNextEntry(entry);
                zos.write(string.getBytes());
                zos.closeEntry();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        zos.close();

        return baos;
    }

    private List<Pessoa> generateModelListFromAFile(MultipartFile file) throws IOException {
        Path tempPath = Path.of(HOME_DIR, "temp", System.currentTimeMillis() + ".txt");
        pessoaFileSystemService.serialize(file.getBytes(), tempPath);
        List<Pessoa> modelList = pessoaFileSystemService.desserialize(tempPath);
        Files.delete(tempPath);

        return modelList;
    }

    private List<String> transformPessoaListInStringList(List<Pessoa> pessoaList) {
        List<String> stringPessoasList = pessoaList.stream()
                .parallel() // realizar o processo com multiplos threads
                .map(signosService::getInformacoesSignosEmString)
                .map(list -> String.join("\n", list))
                .collect(Collectors.toList());

        return stringPessoasList;
    }
}
