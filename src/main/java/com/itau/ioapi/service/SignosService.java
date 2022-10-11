package com.itau.ioapi.service;

import com.itau.ioapi.model.Pessoa;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SignosService {

    public void imprimirInformacoesSignos(Pessoa pessoa) {
        getInformacoesSignosEmString(pessoa).forEach(System.out::println);
    }

    public List<String> getInformacoesSignosEmString(Pessoa pessoa) {
        System.out.println( "Passando no getInformacoes -> Thread: "+Thread.currentThread().getName());
//        try {
//            Thread.sleep(10);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
        List<String> stringList = new ArrayList<>();
        stringList.add(pessoa.getPrimeiroNomeMaisculo());
        stringList.add(String.format("NOME COMPLETO: %s", pessoa.getNome()));
        stringList.add(String.format("IDADE: %s ANOS", pessoa.getIdadeAtual()));
        stringList.add(String.format("SIGNO: %s", pessoa.getSigno()));
        stringList.add(String.format("ASCENDENTE: %s", pessoa.getAscendente()));
        stringList.add(String.format("SIGNO LUNAR: %s", pessoa.getSignoLunar()));
        stringList.add(String.format("NASCEU EM ANO BISSEXTO: %s", pessoa.getAnoBissexto()));
        stringList.add(String.format("=====================%n"));

        return stringList;
    }
}
