package org.pmacik.sewertetris.data.encryption;

/**
 * Třída, která umí (de)šifrovat String.
 *
 * @author Pavel Macík (xmacik01)
 */
public class Encryptor {

   /**
    * Metoda pro šifrování textového řetězce
    */
   public static String encrypt(String decryptedString) {
      int a = (int) (Math.random() * 3 + 1);
      int b = (int) (Math.random() * 3 + 1);
      String data = String.valueOf((char) (Math.random() * 74 + 48))
            + String.valueOf((char) (Math.random() * 74 + 48))
            + String.valueOf(a + 1)
            + String.valueOf((char) (Math.random() * 74 + 48))
            + String.valueOf((char) (Math.random() * 74 + 48))
            + String.valueOf(b + 2)
            + String.valueOf((char) (Math.random() * 74 + 48))
            + String.valueOf((char) (Math.random() * 74 + 48));
      for (int i = 0; i < decryptedString.length(); i++) {
         data += decryptedString.charAt(i);
         int t = (i % 2 == 0) ? a : b;
         for (int ia = 0; ia < t; ia++) {
            data += Character.toString((char) (Math.random() * 74 + 48));
         }
      }

      return reverse(data);
   }

   /**
    * Metoda pro dešifrování textového řetězce
    */
   public static String decrypt(String encryptedString) {
      encryptedString = reverse(encryptedString);
      int a = Integer.parseInt(encryptedString.substring(2, 3)) - 1;
      int b = Integer.parseInt(encryptedString.substring(5, 6)) - 2;
      String data = "";
      String regular = encryptedString.substring(8, encryptedString.length());
      int lenght = regular.length();
      for (int i = 0, ii = 0; i < lenght; i += ((ii++ % 2 == 0) ? a : b) + 1) {
         data += regular.charAt(i);
      }

      return data;
   }

   /**
    * Metoda pro obrácení textového řetězce
    */
   private static String reverse(String stringToReverse) {
      char[] hlp = stringToReverse.toCharArray();
      char[] hlp2 = new char[hlp.length];
      int count = hlp.length;
      for (int i = count - 1; i >= 0; i--) {
         hlp2[count - 1 - i] = hlp[i];
      }
      return String.copyValueOf(hlp2);
   }
}
