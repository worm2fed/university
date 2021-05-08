package com.worm2fed.kursach_prog;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class AssemblerResultActivity extends Activity {

	protected void onCreate(Bundle savedInstanceState) {
		// создаём активити
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_assembler_result);

		String file = getIntent().getExtras().getString("result_type");
		String line = null, mes = null, text = "";
		BufferedReader bf_reader = null;

		try {
			// открываем поток для чтения
			FileReader file_reader = new FileReader(file);
			bf_reader = new BufferedReader(file_reader);

			// читаем содержимое
			while ((line = bf_reader.readLine()) != null) {
				text = text + line + "\n";
			}
		} catch (FileNotFoundException e) {
			mes = "File " + file + " not found!";
			Toast.makeText(getApplicationContext(), mes, Toast.LENGTH_SHORT)
					.show();
		} catch (IOException e) {
			mes = "Error due reading file " + file + "!";
			Toast.makeText(getApplicationContext(), mes, Toast.LENGTH_SHORT)
					.show();
		} finally {
			if (closeFile(bf_reader) == false) {
				mes = "Error due closing file " + file + "!";
				Toast.makeText(getApplicationContext(), mes, Toast.LENGTH_SHORT)
						.show();
			}
		}
		TextView result = (TextView) findViewById(R.id.asm_result);
		result.setText(text);
	}

	// функция для закрытия потока чтения
	private boolean closeFile(BufferedReader bf_reader) {
		try {
			if (bf_reader != null)
				bf_reader.close();
		} catch (IOException e) {
			return false;
		}

		return true;
	}
}