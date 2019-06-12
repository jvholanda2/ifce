package RoudRobin;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Teste {

	public static void main(String[] args) throws NumberFormatException, IOException {
		
		ArrayList<Processo> fila = new ArrayList<>();
		int quantum;
		int qtdProcessos;
	
		FileReader arch = null;
		try {
			arch = new FileReader("/home/fagner/procs");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		BufferedReader read = new BufferedReader(arch);
		
		String quanProc[] = read.readLine().split(" ");  
		quantum = Integer.parseInt(quanProc[0]);
		qtdProcessos = Integer.parseInt(quanProc[1]);
		
		String proc = read.readLine();
		while(proc!=null){
			
			String temp[] = proc.split(" ");
			
			int elemento = Integer.parseInt(temp[0]);
			int tempoRestante = Integer.parseInt(temp[1]);
			int prioridade = Integer.parseInt(temp[2]);
			
			Processo novoProc = new Processo(elemento,tempoRestante,prioridade);
			fila.add(novoProc);
			proc = read.readLine();
			
		}
		
		RoundRobin escalonador = new RoundRobin(quantum,qtdProcessos);
		escalonador.Executar(fila);
		
		System.out.println("Tempo de vazao: "+ escalonador.getVazao());
		System.out.println("Tempo de retorno medio : "+ escalonador.getTempRetornoMedia());
		System.out.println("Tempo de resposta medio : "+ escalonador.getTempRespostaMedia());
		
		
	
	
	}

}
