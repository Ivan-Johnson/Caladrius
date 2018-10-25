package edu.ua.cs.cs495.caladrius.android;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class LoginScreen extends AppCompatActivity
{

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_screen);

		final Button btnLogin = findViewById(R.id.btnLogin);

		btnLogin.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				final TextView txtUser = findViewById(R.id.txtUser);
				final TextView txtPass = findViewById(R.id.txtPass);
				if (txtUser.getText()
				           .toString()
				           .equals("") && txtPass.getText()
				                                 .toString()
				                                 .equals("")) {
					Toast.makeText(v.getContext(), "Login Successful", Toast.LENGTH_SHORT)
					     .show();
					Intent nextScreen = new Intent(v.getContext(), PagerActivity.class);
					startActivityForResult(nextScreen, 0);
				} else {
					Toast.makeText(v.getContext(), "Login Failed", Toast.LENGTH_SHORT)
					     .show();
				}
			}
		});
	}
}
