package com.main.modelo;

import java.util.List;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class EnumTeste {

	private final String str1;
	private final String str2;

	private final Type tipo;

	public static enum Type {
		AGUA, PÓ, ESPUMA, GÁS_INERTE
	}

	public static List<EnumTeste> filterByType(List<EnumTeste> lista, Type type) {

		return lista.stream().filter(x -> x.getTipo().equals(type)).toList();

	}

}