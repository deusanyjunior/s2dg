package org.ufpb.s2dg.session;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.ufpb.s2dg.entity.Usuario;

/**
 * Classe de Utilitarios gerais do sistema
 * 
 * @author dienert
 * 
 */
public class Utils {

	public static boolean validatePassword(String plainPassword, Usuario user) {
		try {
			MessageDigest digestGen = MessageDigest.getInstance("SHA-1");
			// Check digests
			if (!digestGen.isEqual(generateHash(plainPassword), user.getSenha())) {
				System.out.println("Senha invalida");
				return false;
			}
			return true;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return false;
	}

	public static byte[] generateHash(String string) {
		char[] password = string.toCharArray();
		// Convertendo char[] para byte[]
		byte[] passwordBytes = new byte[2 * password.length];
		for (int i = 0, count = 0; i < password.length; i++, count += 2) {
			passwordBytes[count] = (byte) (password[i] & 0xff);
			passwordBytes[count + 1] = (byte) (password[i] >> 8 & 0xff);
		}
		try {
			// Gerando o digest do password
			MessageDigest digestGen = MessageDigest.getInstance("SHA-1");
			byte[] passwordDigest = digestGen.digest(passwordBytes);
			return passwordDigest;
		}// Nunca deveria ser lancada
		catch (NoSuchAlgorithmException ex) {
			ex.printStackTrace();
		}
		return null;
	}

	public static String getHexString(byte[] b) throws Exception {
		String result = "";
		for (int i = 0; i < b.length; i++) {
			result += Integer.toString((b[i] & 0xff) + 0x100, 16).substring(1);
		}
		return result;
	}
	
}
