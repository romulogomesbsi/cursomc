package com.romnulodiego.cursomc;

import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.romnulodiego.cursomc.domain.Categoria;
import com.romnulodiego.cursomc.domain.Cidade;
import com.romnulodiego.cursomc.domain.Cliente;
import com.romnulodiego.cursomc.domain.Endereco;
import com.romnulodiego.cursomc.domain.Estado;
import com.romnulodiego.cursomc.domain.Pagamento;
import com.romnulodiego.cursomc.domain.PagamentoComBoleto;
import com.romnulodiego.cursomc.domain.PagamentoComCartao;
import com.romnulodiego.cursomc.domain.Pedido;
import com.romnulodiego.cursomc.domain.Produto;
import com.romnulodiego.cursomc.domain.enums.EstadoPagamento;
import com.romnulodiego.cursomc.domain.enums.TipoCliente;
import com.romnulodiego.cursomc.repositories.CategoriaRepository;
import com.romnulodiego.cursomc.repositories.CidadeRepository;
import com.romnulodiego.cursomc.repositories.ClienteRepository;
import com.romnulodiego.cursomc.repositories.EnderecoRepository;
import com.romnulodiego.cursomc.repositories.EstadoRepository;
import com.romnulodiego.cursomc.repositories.PagamentoRepository;
import com.romnulodiego.cursomc.repositories.PedidoRepository;
import com.romnulodiego.cursomc.repositories.ProdutoRepository;

@SpringBootApplication
public class CursomcApplication implements CommandLineRunner {
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private EstadoRepository estadoRepository;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private EnderecoRepository enderecoRepository;

	@Autowired
	private PedidoRepository pedidoRepository;
	
	@Autowired
	private PagamentoRepository pagamentoRepository;
	
	public static void main(String[] args) {
		SpringApplication.run(CursomcApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		///CATEGORIAS E PRODUTOS
		Categoria cat1 = new Categoria(null, "Informática");
		Categoria cat2 = new Categoria(null, "Escritório");
		Categoria cat3 = new Categoria(null, "Suporte");
		
		Produto p1 = new Produto(null, "Computador", 2000.00);
		Produto p2 = new Produto(null, "Impressora", 800.00);
		Produto p3 = new Produto(null, "Mouse", 80.00);
		
		cat1.getProdutos().addAll(Arrays.asList(p1, p2,p3));
		cat2.getProdutos().addAll(Arrays.asList(p2));
		
		p1.getCategorias().addAll(Arrays.asList(cat1));
		p2.getCategorias().addAll(Arrays.asList(cat1,cat2));
		p3.getCategorias().addAll(Arrays.asList(cat1));
		
		
		categoriaRepository.saveAll(Arrays.asList(cat1, cat2,cat3));
		produtoRepository.saveAll(Arrays.asList(p1, p2,p3));
		
		///ESTADOS E CIDADES
		Estado est1 = new Estado(null, "Minas Gerais");
		Estado est2 = new Estado(null, "São Paulo");
		Estado est3 = new Estado(null, "Paraíba");
		
		Cidade c1 = new Cidade(null, "Uberlândia", est1);
		Cidade c2 = new Cidade(null, "São Paulo", est2);
		Cidade c3 = new Cidade(null, "Campina Grande", est3);
		Cidade c4 = new Cidade(null, "Campinas", est2);
		
		est1.getCidades().addAll(Arrays.asList(c1));
		est2.getCidades().addAll(Arrays.asList(c2,c4));
		est3.getCidades().addAll(Arrays.asList(c3));
		
		estadoRepository.saveAll(Arrays.asList(est1,est2,est3));
		cidadeRepository.saveAll(Arrays.asList(c1,c2,c3,c4));
		
		///CLIENTE E ENDERECOS
		Cliente cli1 = new Cliente(null, "Maria Silva","Maria@gmail.com","36512365499",TipoCliente.PESSOAFISICA);
		cli1.getTelefones().addAll(Arrays.asList("98765431","126549874"));
		
		Endereco e1 = new Endereco(null, "Rua Flores", "300", "apto 303", "Jardim", "6655449988", cli1, c1);
		Endereco e2 = new Endereco(null, "Avenida Matos", "105", "Sala 800", "Centro", "987654632", cli1, c2);
		
		cli1.getEnderecos().addAll(Arrays.asList(e1,e2));
		
		clienteRepository.saveAll(Arrays.asList(cli1));
		enderecoRepository.saveAll(Arrays.asList(e1,e2));
		
		///PEDIDOS E PAGAMENTOS
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm"); 
		 
		Pedido ped1 = new Pedido(null, sdf.parse("30/09/2017 10:32"), cli1, e1); 
		Pedido ped2 = new Pedido(null, sdf.parse("10/10/2017 19:35"), cli1, e2); 
		     
		cli1.getPedidos().addAll(Arrays.asList(ped1, ped2)); 
		 
		Pagamento pagto1 = new PagamentoComCartao(null, EstadoPagamento.QUITADO, ped1, 6); 
		ped1.setPagamento(pagto1);
		
		Pagamento pagto2 = new PagamentoComBoleto(null, EstadoPagamento.PENDENTE, ped2, sdf.parse("20/10/2017 00:00"), null); 
				ped2.setPagamento(pagto2); 
				 
		pedidoRepository.saveAll(Arrays.asList(ped1, ped2)); 
		pagamentoRepository.saveAll(Arrays.asList(pagto1, pagto2));

	}

}
