package com.proyecto7.docedeseosbackend.utils;

/**
 * Clase utilitaria para encriptar y desencriptar contraseñas utilizando un nombre de usuario como clave.
 * Esta clase no puede ser instanciada.
 */

public class Encrypter {

    private Encrypter() {}

    /**
     * Encripta una contraseña utilizando un nombre de usuario.
     *
     * @param password la contraseña a encriptar.
     * @param username el nombre de usuario que se usará para la encriptación.
     * @return la contraseña encriptada como una cadena de caracteres.
     */

    public static String encrypt(String password, String username){

        if(password.length() == 0){
            return "";
        }

        int max = 20;
        int i = 0;
        String outString = "";
        int l = 33 + password.length();
        outString += (char) l;

            for(; i < password.length(); i++){

                int j = i % username.length();

                int asciiPass = (int) password.charAt(i);
                int asciiUser = (int) username.charAt(j);

                int sum = asciiPass + asciiUser;
                int rem = (sum % 94) + 33;

                outString += (char) rem;
            }

            for(; i < max; i++){

                int j = i % username.length();

                int asciiUser = (int) username.charAt(j);

                int sum = asciiUser + i;
                int rem = (sum % 94) + 33;

                outString += (char) rem;
            }

        return outString;
    }

    /**
     * Desencripta una contraseña encriptada utilizando el nombre de usuario correspondiente.
     *
     * @param encryptedPassword la contraseña encriptada que se desea desencriptar.
     * @param username el nombre de usuario que se usó para la encriptación.
     * @return la contraseña desencriptada como una cadena de caracteres.
     */
    public static String decrypt(String encryptedPassword, String username){

        if(encryptedPassword.length() == 0){
            return "";
        }

        String outString = "";
        int m = encryptedPassword.charAt(0);
        int l = m - 33;
        System.out.println(l);

        for(int i = 1; i < l+1; i++){

            int j = (i-1) % username.length();

            int asciiEncrypted = (int) encryptedPassword.charAt(i);

            int asciiUser = (int) username.charAt(j);

            int sum = asciiEncrypted - 33 - asciiUser;

            while(sum < 33){
                sum += 94;
            }

            outString += (char) sum;
        }

        return outString;
    }
}
