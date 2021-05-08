package com.worm2fed.kursach_prog;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class RunActivity extends Activity {
	private String mes = null, path;
	private int mem_size, mode_id;
	private ALU alu;
	TextView reg1, reg2, reg_flag, mem_left, last_ten_data;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_run);

		// получаем данные из основного активити
		mem_size = getIntent().getExtras().getInt("mem_size");
		mode_id = getIntent().getExtras().getInt("mode_id");
		path = getIntent().getExtras().getString("path");

		// инициализируем наши текстовые поля
		reg1 = (TextView) findViewById(R.id.reg1);
		reg2 = (TextView) findViewById(R.id.reg2);
		reg_flag = (TextView) findViewById(R.id.reg_flag);
		mem_left = (TextView) findViewById(R.id.mem_left);
		last_ten_data = (TextView) findViewById(R.id.last_ten_data);

		// создаём АЛУ
		alu = new ALU(mem_size, path);
	}

	public String[] getLeftData() {
		byte left_data[] = alu.returnMemory().showLeftData(
				alu.returnMemorySizeValue());
		String list_arr[] = new String[left_data.length];

		for (int i = 0; i < left_data.length; i++)
			list_arr[i] = i + ": " + left_data[i];

		return list_arr;
	}

	// обработчик нажатия на кнопку 'показать оставшиеся данные'
	public void onShowMemLeftClick(View view) {
		AlertDialog.Builder mem_dialog = new AlertDialog.Builder(this);

		mem_dialog.setTitle(R.string.mem_left).setCancelable(false);

		// добавляем список с данными
		ListView mem_list = new ListView(this);

		String[] list_arr = getLeftData();

		ArrayAdapter<String> list_adap = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, list_arr);
		mem_list.setAdapter(list_adap);

		mem_dialog.setView(mem_list);

		// добавляем одну кнопку для закрытия диалога
		mem_dialog.setNegativeButton(android.R.string.cancel,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						dialog.cancel();
					}
				});

		mem_dialog.show();
	}

	public void onRunAluClick(View view) {
		// непосрдетсвенно выполнение
		switch (mode_id) {
		// автоматический режим
		case 0:
			mes = alu.runAutoMode();
			break;
		// ручной режим
		case 1:
			mes = alu.runManualMode();
			break;
		// ошибка
		default:
			mes = getApplicationContext().getResources().getString(
					R.string.mode_error);
			break;
		}

		// выводим соответствующие сообщения
		switch (mes) {
		case "prepare_err":
			mes = getApplicationContext().getResources().getString(
					R.string.prepare_error);
			break;
		case "jo":
			mes = getApplicationContext().getResources().getString(
					R.string.jo_err);
			break;
		case "ex_err":
			mes = getApplicationContext().getResources().getString(
					R.string.ex_error);
			break;
		case "suc":
			mes = getApplicationContext().getResources().getString(
					R.string.success_run);
			break;
		default:
			mes = null;
			break;
		}

		if (mes != null)
			Toast.makeText(getApplicationContext(), mes, Toast.LENGTH_SHORT)
					.show();

		reg1.setText(Byte.toString(alu.returnReg1Value()));
		reg2.setText(Byte.toString(alu.returnReg2Value()));

		switch (alu.returnRegFlagValue()) {
		// overflow
		case 0x01:
			reg_flag.setText("overflow");
			break;
		// zero
		case 0x08:
			reg_flag.setText("zero");
			break;
		// negative
		case 0x04:
			reg_flag.setText("negative");
			break;
		// positive
		case 0x02:
			reg_flag.setText("positive");
			break;
		default:
			reg_flag.setText(Integer.toHexString(alu.returnRegFlagValue()));
			break;
		}

		mem_left.setText(Integer.toString(alu.returnMemorySizeValue()));

		String[] data = getLeftData();
		String text = "";
		if (data.length > 10)
			for (int i = 0; i < 10; i++)
				text = text + data[i] + "\n";
		else
			for (int i = 0; i < data.length; i++)
				text = text + data[i] + "\n";

		last_ten_data.setText(text);
	}
}