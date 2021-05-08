package com.worm2fed.kursach_prog;

import com.worm2fed.kursach_prog.OpenFileDialog.OpenDialogListener;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	private String file_path = "", path = "";
	Intent asembler_result_activity, run_activity;
	String[] nm;
	int mem_size, mode_id = 0;
	// хранит результат ассемблирования
	private boolean asm_res = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// создаём активити
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// размер памяти
		mem_size = 256;
	}

	// обработчик нажатия на кнопку 'выбрать файл'
	public void onOpenFileClick(View view) {
		String message = "";
		// проверка на доступность SD-карты
		if (!Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			message = getApplicationContext().getResources().getString(
					R.string.sdcard_error);
			Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT)
					.show();
		} else {
			// создаём список с файлами для выбора
			OpenFileDialog fileDialog = new OpenFileDialog(this);
			fileDialog.show();

			// создаём слушатель для получения путя к файлу при его выборе из
			// спсика
			fileDialog.setOpenDialogListener(new OpenDialogListener() {
				@Override
				public void OnSelectedFile(String fileName) {
					// TODO Auto-generated method stub

					// Связываем объект GUI с переменной
					TextView filePath = (TextView) findViewById(R.id.file_path);
					// выводим путь файла рядом с кнопкой
					filePath.setText(fileName);
					// переменная, хранящаяя путь к файлу
					file_path = fileName;
					path = "";

					nm = file_path.split("/");
					for (int i = 0; i < (nm.length - 1); i++)
						path = path + nm[i] + "/";
				}
			});
		}
	}

	// обработчик нажатия на кнопку 'преобразовать в объектный код'
	public void onAssemblClick(View view) {
		String message = "";
		if (file_path.equals(""))
			message = getApplicationContext().getResources().getString(
					R.string.notchosen_error);
		else {
			Assembler assembler = new Assembler(file_path);
			String mes = assembler.toAssembl();

			// вытасикаем сообщение о результате ассемблирования из ресурсов
			switch (mes) {
			case "ext_err":
			case "read_err":
			case "close_err":
				message = getApplicationContext().getResources().getString(
						R.string.extention_error);
				break;
			case "lis_err":
				message = getApplicationContext().getResources().getString(
						R.string.listing_error);
				break;
			case "obj_err":
				message = getApplicationContext().getResources().getString(
						R.string.object_error);
				break;
			case "404_err":
				message = getApplicationContext().getResources().getString(
						R.string.notfound_error);
				break;
			case "suc":
				message = getApplicationContext().getResources().getString(
						R.string.success_asm);
				asm_res = true;
				break;
			}
		}

		Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT)
				.show();
	}

	// обработчик нажатия на кнопку 'показать объектный код'
	public void onShowObjectCodeClick(View view) {
		String message = "";
		// записываем путь к файлу объектного кода
		message = path + "object_code.w2f";

		// инициализируем будущий переход между активити
		asembler_result_activity = new Intent(MainActivity.this,
				AssemblerResultActivity.class);
		// передаём значение переменной в другое активити
		asembler_result_activity.putExtra("result_type", message);
		// запускаем активити
		startActivity(asembler_result_activity);
	}

	// обработчик нажатия на кнопку 'показать листинг'
	public void onShowListingClick(View view) {
		String message = "";
		// записываем путь к файлу листинга
		message = path + "listing.w2f";

		// инициализируем будущий переход между активити
		asembler_result_activity = new Intent(MainActivity.this,
				AssemblerResultActivity.class);
		// передаём значение переменной в другое активити
		asembler_result_activity.putExtra("result_type", message);
		// запускаем активити
		startActivity(asembler_result_activity);
	}

	// обработчик нажатия на кнопку 'выполнить'
	public void onRunClick(View view) {
		String message = "";
		if (asm_res == false) {
			message = getApplicationContext().getResources().getString(
					R.string.notasm_error);
			Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT)
					.show();
		} else {
			// открываем диалог для выбора режима выполнения
			openRunDialog();
		}
	}

	// окошко для выбора режима выполнения
	protected void openRunDialog() {
		final String[] m_сhoose = getResources().getStringArray(
				R.array.mode_arr);
		AlertDialog.Builder run_dialog = new AlertDialog.Builder(this);

		run_dialog.setTitle(R.string.mode).setCancelable(false);
		// добавляем переключатели
		run_dialog.setSingleChoiceItems(m_сhoose, 0,
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int item) {
						mode_id = item;
					}
				});

		run_dialog.setPositiveButton(android.R.string.yes,
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub

						// инициализируем будущий переход между активити
						run_activity = new Intent(MainActivity.this,
								RunActivity.class);
						// передаём значение переменных в другое активити
						run_activity.putExtra("mem_size", mem_size);
						run_activity.putExtra("mode_id", mode_id);
						run_activity.putExtra("path", path);
						// запускаем активити
						startActivity(run_activity);
					}
				});

		// добавляем одну кнопку для закрытия диалога
		run_dialog.setNegativeButton(android.R.string.cancel,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						dialog.cancel();
					}
				});

		run_dialog.show();
	}
}