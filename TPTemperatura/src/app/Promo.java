package app;

import Utilities.TecladoIn;

/**
 *
 * @authores Thomas Alejo Mora (FAI-2829)
 * @authores Nahuel Matias Tello (FAI-2904)
 */
public class Promo {

    public static boolean validarTemperatura(String tempIngresada) {

        /**
         * este módulo retorna TRUE si la temperatura que se ingresa por parámetro
         * cumple con el formato para ser tomado como temperatura valida
         */
        /**
         * FORMATO: Para Celcius (C): +tt:C, -tt:C Para Fahrenheit (F): +tt:F, -tt:F
         * "tt" representa una temperatura, a dos digitos
         *
         */
        boolean tempIngresadaValida = false;
        if (tempIngresada.length() == 5) {
            final char signo = tempIngresada.charAt(0);
            final boolean primerNumeroEsDigito = Character.isDigit(tempIngresada.charAt(1));
            final boolean segundoNumeroEsDigito = Character.isDigit(tempIngresada.charAt(2));
            final char dosPuntos = tempIngresada.charAt(3);
            final char escala = tempIngresada.charAt(4);
            tempIngresadaValida = ((signo == '+' || signo == '-') && primerNumeroEsDigito && segundoNumeroEsDigito
                    && dosPuntos == ':' && (escala == 'F' || escala == 'C' || escala == 'f' || escala == 'c'));
        }
        return tempIngresadaValida;
    }

    /* MODULO EXPLICACION DE NO VALIDACION DEL TEMPERATURA */
    public static String porQueNoEsValidaTemp(String tempIngresada) {
        /**
         * Este módulo recibe una temperatura por parámetro y en el caso de que sea
         * inválida, devuelve un String con la respuesta de por qué lo es.
         */
        char signo = 'a', dosPuntos = 'a', escala = 'a';
        boolean primerNumeroEsDigito = false, segundoNumeroEsDigito = false;

        String porque = "La temperatura ingresada está mal porque:\n";
        if (tempIngresada.length() == 5) {
            signo = tempIngresada.charAt(0);
            primerNumeroEsDigito = Character.isDigit(tempIngresada.charAt(1));
            segundoNumeroEsDigito = Character.isDigit(tempIngresada.charAt(2));
            dosPuntos = tempIngresada.charAt(3);
            escala = tempIngresada.charAt(4);

            if (!(signo == '+' || signo == '-')) {
                porque = porque + " - El signo no es + ni -\n";
            }
            if (!(primerNumeroEsDigito && segundoNumeroEsDigito)) {
                porque = porque + " - La temperatura (números del 0 a 99) no son números.\n";
            }
            if (dosPuntos != ':') {
                porque = porque
                        + " - No se ingresaron los ':' (dos puntos) en el lugar donde deberían haberse ingresado.\n";
            }
            if (!((escala == 'F' || escala == 'f') || (escala == 'C' || escala == 'c'))) {
                porque = porque
                        + " - La escala tiene que ser en Celcius (C) o Fahrenheit (F), ambos tienen que ser escritos en mayúscula. Ud escribio '"
                        + tempIngresada.charAt(4) + "'";
            }
        } else {
            porque = porque + " Usted escribio una temperatura de : " + tempIngresada.length()
                    + " caracteres, por lo tanto no es correcta \n" + "- Debe tener 5 caracteres.";
        }
        return porque;
    }

    public static boolean verSiLaPrimerTempEsMasFria(String temp1, String temp2) {
        /**
         * Este módulo recibe por parametro 2 temperaturas y devuelve TRUE o FALSE si la
         * primera es más fría que la segunda.
         *
         * @param temp1 --> temperatura 1, la que veremos si es mas fría que la otra.
         * @param temp2 --> temperatura 2, con la que compararemos la temperatura 1.
         */
        boolean temp1Masfria = false;
        int temp1num = 00, temp2num = 00;
        char signoTempUno = temp1.charAt(0);
        char signoTempDos = temp2.charAt(0);

        if (validarTemperatura(temp1) && validarTemperatura(temp2)) {
            if (temp1.charAt(4) == temp2.charAt(4)) {
                temp1num = Integer.parseInt(temp1.substring(1, 3));
                temp2num = Integer.parseInt(temp2.substring(1, 3));
                if (!temp1.equals(temp2)) {
                    if (signoTempUno == '+' && signoTempDos == '+') {
                        if (temp1num < temp2num) {
                            temp1Masfria = true;
                        }
                    } else if (signoTempUno == '-' && signoTempDos == '+') {
                        temp1Masfria = true;
                    } else if (signoTempUno == '-' && signoTempDos == '-') {
                        if (temp1num > temp2num) {
                            temp1Masfria = true;
                        }
                    }
                }
            } else {
                temp1Masfria = verSiLaPrimerTempEsMasFria(temp1, cambiarEscala(temp2));
            }
        } else {
            System.out.println("Una de las temperaturas que ingresó no cumple con el formato.");
        }

        return temp1Masfria;
    }

    public static void compararDosTemperaturas(String temp1, String temp2) {
        /**
         * Este módulo recibe dos temperaturas y ejecuta por pantalla (salida) que es la
         * temperatura 1 con respecto a la temperatura 2. Si son iguales, o la mas fria.
         *
         * @param temp1 --> primer temperatura a comparar
         * @param temp2 --> segunda temperatura a comparar con la primera
         */
        final String temp1MasFria = "Las temperaturas son diferentes. La primer temperatura (" + temp1
                + ") es más fría que la segunda (" + temp2 + ").";
        final String temp2MasFria = "Las temperaturas son diferentes. La segunda temperatura (" + temp2
                + ") es más fría que la primera (" + temp1 + ").";
        final String tempsIguales = "Las temperaturas, tanto " + temp1 + " y " + temp2 + " son iguales";
        /* Validacion de Temperaturas */
        if (validarTemperatura(temp1) && validarTemperatura(temp2)) {
            System.out.println("--------------------------------------------------------------");
            System.out.println("Validacion de temperaturas: " + temp1 + " y " + temp2 + " CORRECTAS");
            System.out.println("--------------------------------------------------------------");
            System.out.println("Se compara " + temp1 + " con " + temp2);
            /* Compara si ambas son iguales */
            if (temp1.equals(temp2)) {
                System.out.println(tempsIguales);
            } else {
                if (verSiLaPrimerTempEsMasFria(temp1, temp2)) {
                    System.out.println(temp1MasFria);
                } else {
                    System.out.println(temp2MasFria);
                }
            }
        } else {
            System.out.println("Ingresó dos o algunas de las temperaturas incorrectas, vuelve a intentar.");
        }
    }

    public static String cambiarEscala(String temp) {
        /*
         * Este modulo consiste en covertir una temperatura de Celcius a Fahrenheit o
         * viceversa
         * 
         * @param temp --> la temperatura a cambiar de escala.
         */
        int tempActual = 00, nuevaTemp = 00;
        char signoTemp = temp.charAt(0);
        char escala = temp.charAt(4);
        char nuevaEscala = ' ';

        /*
         * Este metodo permite convertir una cadena de caracteres que contiene un
         * numero, en el numero enteroequivalente.
         */
        tempActual = Integer.parseInt(temp.substring(1, 3));

        String nuevaTemperatura = "";

        if (escala == 'C') {
            nuevaTemp = (int) ((tempActual * (9 / 5.0)) + 32);
            nuevaEscala = 'F';
        } else {
            // escala == 'F';
            nuevaTemp = (int) ((tempActual - 32) / (9 / 5.0));
            nuevaEscala = 'C';
        }

        if (nuevaTemp >= 10 || nuevaTemp <= -10) {
            nuevaTemperatura = "" + signoTemp + nuevaTemp + ":" + nuevaEscala;
        } else {
            // para números con un solo digito, hay que ingresar un cero adelante de ellos.
            nuevaTemperatura = "" + signoTemp + "0" + nuevaTemp + ":" + nuevaEscala;
        }

        System.out.println(temp + " pasó a valer " + nuevaTemperatura);

        return nuevaTemperatura;
    }

    public static void aumentarUnGrado(String temp) {
        /**
         * Este módulo recibe una temperatura y aumenta un grado en su escala. Muestra
         * por salida el resultado.
         *
         * @param temp --> temperatura a aumentar un grado.
         */
        final int tempNum = Integer.parseInt(temp.substring(1, 3)) + 1;
        final String nuevaTemp = "" + temp.charAt(0) + tempNum + ":" + temp.charAt(4);

        System.out.println("Si aumentamos " + temp + " en un grado de su escala, da como resultado " + nuevaTemp);
    }

    public static String tempMasBajaDeLaSecuencia(String secuencia) {

        /**
         * Este módulo recibe por parametro una secuencia de temperaturas y por salida
         * nos dice cual es la mas calurosa
         */
        // @param secuenciaTemps representa la secuencia de temperaturas a revisar.
        int i = 0;
        final int cantTemps = secuencia.length() / 5;
        String tempMasBaja = "+99:C";
        boolean esMasBaja = false;

        System.out.println("Ingresó " + cantTemps + " temperatura/s.");
        for (i = 0; i < cantTemps * 5; i = i + 5) {
            esMasBaja = false;
            String tempActual = secuencia.substring(i, i + 5);
            if (validarTemperatura(tempActual)) {
                esMasBaja = verSiLaPrimerTempEsMasFria(tempActual, tempMasBaja);
                if (esMasBaja) {
                    tempMasBaja = tempActual;
                }
            } else {
                System.out.println("La temperatura " + tempActual + " no cumple con el formato, no se evaluará.");
            }
        }

        return tempMasBaja;
    }

    public static String tempMasAltaDeLaSecuencia(String secuencia) {
        /**
         * Este módulo recibe por parametro una secuencia de temperaturas y por salida
         * nos dice cual es la mas calurosa
         */
        // @param secuenciaTemps representa la secuencia de temperaturas a revisar.

        int i = 0;
        final int cantTemps = secuencia.length() / 5;
        String tempMasAlta = "-99:C";
        boolean esMasAlta = false;

        System.out.println("Ingresó " + cantTemps + " temperatura/s.");

        for (i = 0; i < cantTemps * 5; i = i + 5) {
            String tempActual = secuencia.substring(i, i + 5);
            esMasAlta = false;
            if (validarTemperatura(tempActual)) {
                esMasAlta = !verSiLaPrimerTempEsMasFria(tempActual, tempMasAlta);
                if (esMasAlta) {
                    tempMasAlta = tempActual;
                }
            } else {
                System.out.println("La temperatura " + tempActual + " no cumple con el formato, no se evaluará.");
            }

        }

        return tempMasAlta;
    }

    public static int buscarTemperaturaEnSecuencia(String secuencia, String tempBuscada) {
        /**
         * Este modulo recibe por parametro una secuencia de temperaturas, dentro
         * pregunta cual es la que se busca, y nos dice mediante salida por pantalla si
         * aparece, y cuantas veces.
         */
        int i = 0;
        final int cantTemps = secuencia.length() / 5;
        int cantVecesQueAparece = 0;
        String tempActual = "";
        System.out.println("Ingresó " + cantTemps + " temperatura/s.");

        for (i = 0; i < cantTemps * 5; i = i + 5) {
            tempActual = secuencia.substring(i, i + 5);
            System.out.println("Comparando " + tempActual + " con " + tempBuscada);
            // compara la temperatura actual con la buscada
            // validacion la tiene que hacer
            if (validarTemperatura(tempActual)) {
                if (tempActual.equals(tempBuscada)) {
                    cantVecesQueAparece++;
                }
            }
        }

        return cantVecesQueAparece;
    }

    public static boolean validarSecuenciaTemps(String secuencia) {
        int i = 0;
        final int cantTemps = secuencia.length() / 5;
        int noValidas = 0;
        String tempActual = "";
        boolean secuenciaValida = false;

        for (i = 0; i < cantTemps * 5; i = i + 5) {
            tempActual = secuencia.substring(i, i + 5);
            if (!validarTemperatura(tempActual)) {
                noValidas++;
            }
        }
        if (noValidas == 0) {
            secuenciaValida = true;
        }
        return secuenciaValida;
    }

    public static String secuenciaDeTemperaturas() {
        /**
         * Este módulo recolecta temperaturas ingresadas una por una dentro de un bucle,
         * una vez que se ingresa una, se pregunta al usuario si desea ingresar otra.
         * cuando el usuario no quiere ingresar más temperaturas, se retorna el String
         * con todas las temperaturas concatenadas una pegada a la otra
         */
        String continuar = "n";
        String tempActual = "";
        String secuencia = "";
        do {
            System.out.print("Ingrese una temperatura: ");
            tempActual = TecladoIn.readLineWord();
            if (validarTemperatura(tempActual)) {
                secuencia = secuencia + tempActual;
            } else {
                System.out.println("Ingresó una temperatura no válida");
                System.out.println(porQueNoEsValidaTemp(tempActual));
            }
            System.out.print("Continuar ingresando temperaturas? (s/n): ");
            continuar = TecladoIn.readLineWord();
        } while (continuar.equalsIgnoreCase("s"));

        return secuencia;
    }

    public static void modulo1() {
        /**
         * Módulo 1: Verifica si un String recibido como parámetro representa una
         * temperatura válida. Se considera válido si respeta la estructura de
         * temperatura mencionada anteriormente. Este m´odulo retorna true si es válido,
         * false en caso contrario.
         */

        String temp; // variable para captar la temperatura a verificar.
        System.out.println(
                "---------------------------------------------------- Verificar si una temperatura es válida -----------------------------------------------------");
        System.out.print("Ingrese una temperatura: ");
        temp = TecladoIn.readLineWord();
        if (validarTemperatura(temp)) {
            System.out.println("La temperatura ingresada cumple con el formato. Por ende es válida");
        } else {
            System.out.println("La temperatura ingresada no cumple con el formato. Por ello no es válida");
        }
    }

    public static void modulo2() {

        /**
         * Módulo 2: Dada una temperatura, determinar porque una temperatura no es
         * válida, el módulo deberá retornar carteles indicativos como ’La temperatura
         * no es C, ni F’, ’La temperatura en su valor numérico no es válido’, ..,etc.
         */
        String temp;
        System.out.println(
                "------------------------------------------------ Verificar por qué una temperatura no es válida ------------------------------------------------");
        System.out.print("Ingrese una temperatura: ");
        temp = TecladoIn.readLineWord();
        if (validarTemperatura(temp)) {
            System.out.println("La temperatura ingresada si cumple con el formato.");
        } else {
            System.out.println(porQueNoEsValidaTemp(temp));
        }
    }

    public static void modulo3() {
        /**
         * Módulo 3: Dados dos Strings (representando temperaturas) recibidos como
         * parámetros verifica si la primera temperatura es ’más fría’ que la segunda
         * temperatura. Deberá comparar temperaturas en igual unidad de medidas. Si no
         * lo son deberá convertir a alguna de ellas a la unidad de medida de la otra
         * temperatura.
         */
        String temp1, temp2;
        System.out.println(
                "------------------------------------------------ Verificar si una temperatura es menor que otra ------------------------------------------------");
        System.out.println("Ingrese la temperatura 1");
        temp1 = TecladoIn.readLineWord();
        System.out.println("Ingrese la temperatura 2");
        temp2 = TecladoIn.readLineWord();

        compararDosTemperaturas(temp1, temp2);
    }

    public static void modulo4() {
        /**
         * Módulo 4: Dados dos Strings recibidos como parámetros verifica si la primera
         * temperatura es igual que la segunda temperatura. Idem anterior, considere que
         * debe comparar en igual unidad de medida.
         */
        String temp1, temp2;
        System.out.println(
                "---------------------------------------------------- Verificar si 2 temperaturas son iguales ----------------------------------------------------");
        System.out.println("Ingrese la temperatura 1");
        temp1 = TecladoIn.readLineWord();
        System.out.println("Ingrese la temperatura 2");
        temp2 = TecladoIn.readLineWord();
        compararDosTemperaturas(temp1, temp2);
    }

    public static void modulo5() {
        /**
         * Solicita al usuario una secuencia de temperaturas correspondientes a un mismo
         * dia, consultando al usuario: ’¿desea continuar (si/no)?’. Finalmente muestra
         * la temperatura m´as fr´ıa.
         */

        String secuenciaTemps = "";
        System.out.println(
                "---------------------------------------------- De una secuencia ¿Cuál es la temperatura mas baja? ----------------------------------------------");
        secuenciaTemps = secuenciaDeTemperaturas();
        final String tempMasBaja = tempMasBajaDeLaSecuencia(secuenciaTemps);

        System.out.println("La temperatura mas baja de la secuencia es: " + tempMasBaja);

    }

    public static void modulo6() {
        /**
         * Solicita al usuario una temperatura determinada, digamos A, luego solicita
         * una secuencia de temperaturas consultando al usuario: ’¿desea continuar
         * (si/no)?’. Finalmente muestra por pantalla si la temperatura A solicitada
         * inicialmente esta duplicada en la secuencia ingresada y cuantas veces
         * ocurrencias se encontraron de la misma.
         */
        String tempBuscada = "";
        String secuenciaTemps = "";
        System.out.println(
                "---------------------------------------------- De una secuencia ¿Cuantas veces se repite una temperatura? ----------------------------------------------");
        secuenciaTemps = secuenciaDeTemperaturas();
        System.out.println("Ingrese la temperatura que busca:");
        tempBuscada = TecladoIn.readLine();
        if (validarTemperatura(tempBuscada)) {
            final int cantVecesQueAparece = buscarTemperaturaEnSecuencia(secuenciaTemps, tempBuscada);
            System.out.println(
                    "Cantidad de veces que aparece la temperatura buscada en la secuencia: " + cantVecesQueAparece);
        } else {
            System.out.println("La temperatura que quiere buscar no es válida");
            System.out.println(porQueNoEsValidaTemp(tempBuscada));
        }
    }

    public static void modulo7() {

        /**
         * Dado un String recibido como par´ametro, representando una temperatura,
         * permite obtener la temperatura con un grado más
         */
        String temp;
        System.out.println(
                "------------------------------------------------ Subir un grado la temperatura ------------------------------------------------");
        System.out.println(
                "Debe respetar el formato [+/-][temperatura en enteros (0 a 99)]:[F/C para Farenheit o Celsius respectivamente]");
        System.out.print("Ingrese una temperatura: ");
        temp = TecladoIn.readLineWord();
        if (validarTemperatura(temp)) {
            aumentarUnGrado(temp);
        } else {
            System.out.println(porQueNoEsValidaTemp(temp));
        }
    }

    public static void modulo8() {

        /**
         * Idem módulo 5, pero la secuencia de temperaturas es recibida en un String que
         * contiene un conjunto de temperaturas. Por ejemplo: ”+01:F-04:C+20:C” el cual
         * contiene 3 temperaturas. Y el módulo debe mostrar la temperatura más
         * calurosa.
         */
        String secuenciaTemps = "";
        System.out.println(
                "------------------------------------------- Temperatura mas calurosa de una secuencia -------------------------------------------");
        secuenciaTemps = "";
        System.out.println("Nota: Se obvia que se ingresen las temperaturas con el formato correspondiente.");
        System.out.print("Por favor, ingrese una secuencia de temperaturas, una pegada a la otra: ");
        secuenciaTemps = TecladoIn.readLineWord();
        if (validarSecuenciaTemps(secuenciaTemps)) {
            final String tempMasAlta = tempMasAltaDeLaSecuencia(secuenciaTemps);
            System.out.println("La temperatura mas alta de la secuencia es: " + tempMasAlta);
        } else {
            System.out.println("Ingreso una secuencia inválida, por favor reinicie el programa.");
        }
    }

    public static void modulo9() {

        /**
         * Idem módulo 6, pero la secuencia de temperaturas es recibida en un String que
         * contiene un conjunto de temperaturas. Por ejemplo: ”+01:F-04:C+20:C” el cual
         * contiene 3 temperaturas.
         */
        String secuenciaTemps = "";
        System.out.println(
                "------------------------------------------- Buscar una temperatura en una secuencia -------------------------------------------");
        System.out.println("Nota: Se obvia que se ingresen las temperaturas con el formato correspondiente.");
        System.out.print("Por favor, ingrese una secuencia de temperaturas, una pegada a la otra: ");
        secuenciaTemps = TecladoIn.readLineWord();
        if (validarSecuenciaTemps(secuenciaTemps)) {
            System.out.print("Qué temperatura de la secuencia desea buscar?: ");
            final String tempBuscada = TecladoIn.readLineWord();
            final int cantidadDeVecesQueAparece = buscarTemperaturaEnSecuencia(secuenciaTemps, tempBuscada);
            System.out.println(
                    "Cantidad de veces que aparece la temperatura en la secuencia: " + cantidadDeVecesQueAparece);
        } else {
            System.out.println("Ingreso una secuencia inválida, por favor reinicie el programa.");
        }

    }

    public static void menu() {
        String iniciar = "s";
        int opcion = 0;
        do {
            if (iniciar.equalsIgnoreCase("s")) {
                System.out.println(
                        "------------------------------------------------------------- Programa de Temperaturas -------------------------------------------------------------\n"
                                + "1 - Verificar temperatura válida\n"
                                + "2 - Determinar porque una temperatura no es válida (HECHO)\n"
                                + "3 - Verificar si una temperatura es más fría que otra.\n"
                                + "4 - Verificar si una temperatura es igual a otra\n"
                                + "5 - Ingresar una secuencia de temperaturas (consultandole si se detiene o no) y obtener la temperatura más fría\n"
                                + "6 - Ingresar una secuencia de temperaturas (consultandole si se detiene o no) y una temperatura determinada A, y hallar cantidad de ocurrencias de A\n"
                                + "7 - Incrementar un grado la temperatura\n"
                                + "8 - Dada una secuencia de temperaturas (en una sola línea), obtener la temperatura más calurosa\n"
                                + "9 - Dada una secuencia de temperaturas (en una sola línea) y una temperatura determinada A, hallar cantidad de ocurrencias de A\n"
                                + "10 - Terminar \n"
                                + "-------------------------------------------------------------------------------------------------------------------------------------------------");

                System.out.print("¿Qué desea hacer?: ");

                opcion = TecladoIn.readInt();

                if (opcion != 10) {
                    System.out.println(
                            "NOTA IMPORTANTE: Debe respetar el formato/estructura [+/-][temperatura en enteros]:[F/C para Farenheit o Celsius respectivamente (en mayus)]");
                }
                switch (opcion) {
                    case 1:
                        modulo1();
                        break;
                    case 2:
                        modulo2();
                        break;
                    case 3:
                        modulo3();
                        break;
                    case 4:
                        modulo4();
                        break;
                    case 5:
                        modulo5();
                        break;
                    case 6:
                        modulo6();
                        break;
                    case 7:
                        modulo7();
                        break;
                    case 8:
                        modulo8();
                        break;
                    case 9:
                        modulo9();
                        break;
                    case 10:
                        iniciar = "n";
                        break;
                    default:
                        System.out.println("La opción ingresada no existe");
                        break;
                }
            }
            if (opcion < 10) {
                System.out.println("-------------------------------------------------------");
                System.out.print("Volver al menu del programa? (s/n): ");
                iniciar = TecladoIn.readLineWord();
            } else {
                System.out.println("Terminando programa.");
            }
        } while (iniciar.equalsIgnoreCase("s") && opcion < 10);
    }

    /* ALGORITMO PRINCIPAL */
    public static void main(String[] args) {
        // Mostrar por pantalla una bienvenida al programa y repetir hasta que diga que
        // no quiera hcaer mas tareas

        System.out.println(
                "-------------------------------------------------------------------------------------------------------------------------------------------------\n"
                        + "                         ||||||  || ||||||| ||||    || ||        || |||||||| ||||    || || |||||||    ||||||||                                   \n"
                        + "                         ||   ||    ||      || ||   ||  ||      ||  ||       || ||   ||    ||     ||  ||    ||                                   \n"
                        + "                         ||  ||  || |||||   ||  ||  ||   ||    ||   ||||||   ||  ||  || || ||      || ||    ||                                   \n"
                        + "                         ||   || || ||      ||   || ||    ||  ||    ||       ||   || || || ||     ||  ||    ||                                  \n"
                        + "                         ||||||  || ||||||| ||    ||||      ||      |||||||| ||    |||| || |||||||    ||||||||                                  \n"
                        + "                                                                                                                                                \n"
                        + "                            ||    ||         |||||||  ||||||| |||||||| |||||||| |||||||    ||    ||||  ||||    ||                               \n"
                        + "                           ||||   ||         ||    || ||   || ||    || ||       ||   ||   ||||   || |||| ||   ||||                              \n"
                        + "                          ||--||  ||         |||||||  ||  ||  ||    || ||  |||| ||  ||   ||--||  ||  ||  ||  ||--||                             \n"
                        + "                         ||    || ||||||     ||       ||   || |||||||| |||||||| ||   || ||    || ||      || ||    ||                            \n"
                        + "-------------------------------------------------------------------------------------------------------------------------------------------------");
        menu();
    }

}
