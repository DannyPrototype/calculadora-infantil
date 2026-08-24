package com.ejemplo.calculadorainfantil;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.app.Activity;
import java.text.DecimalFormat;

public class MainActivity extends Activity {
    private TextView pantalla;
    private double primerNumero = 0;
    private String operacion = "";
    private boolean comenzarNumero = true;
    private final DecimalFormat formato = new DecimalFormat("0.########");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pantalla = findViewById(R.id.pantalla);
    }

    public void escribirNumero(View view) {
        String tecla = ((Button) view).getText().toString();
        String actual = pantalla.getText().toString();

        if (comenzarNumero || actual.equals("0") || actual.equals("¡Ups!")) {
            actual = "";
            comenzarNumero = false;
        }

        if (tecla.equals(".") && actual.contains(".")) return;
        if (tecla.equals(".") && actual.isEmpty()) actual = "0";
        pantalla.setText(actual + tecla);
    }

    public void elegirOperacion(View view) {
        if (!operacion.isEmpty() && !comenzarNumero) calcularResultado();
        primerNumero = leerPantalla();
        operacion = ((Button) view).getTag().toString();
        comenzarNumero = true;
    }

    public void mostrarResultado(View view) {
        calcularResultado();
        operacion = "";
    }

    private void calcularResultado() {
        if (operacion.isEmpty() || comenzarNumero) return;
        double segundoNumero = leerPantalla();
        double resultado;

        switch (operacion) {
            case "+": resultado = primerNumero + segundoNumero; break;
            case "-": resultado = primerNumero - segundoNumero; break;
            case "*": resultado = primerNumero * segundoNumero; break;
            case "/":
                if (segundoNumero == 0) {
                    pantalla.setText("¡Ups!");
                    comenzarNumero = true;
                    operacion = "";
                    return;
                }
                resultado = primerNumero / segundoNumero;
                break;
            default: return;
        }

        pantalla.setText(formato.format(resultado));
        primerNumero = resultado;
        comenzarNumero = true;
    }

    public void limpiar(View view) {
        pantalla.setText("0");
        primerNumero = 0;
        operacion = "";
        comenzarNumero = true;
    }

    private double leerPantalla() {
        try {
            return Double.parseDouble(pantalla.getText().toString());
        } catch (NumberFormatException error) {
            return 0;
        }
    }
}
