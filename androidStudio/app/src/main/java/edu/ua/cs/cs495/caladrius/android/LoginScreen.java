package edu.ua.cs.cs495.caladrius.android;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import edu.ua.cs.cs495.caladrius.fitbit.FitbitAccount;

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

				String user = txtUser.getText().toString();
				String pass = txtPass.getText().toString();

				try {
					Caladrius.user = new FitbitAccount(user, pass);
				} catch (IllegalArgumentException ex) {
					Toast.makeText(v.getContext(), "Login Failed", Toast.LENGTH_SHORT).show();
					return;
				}

				Toast.makeText(v.getContext(), "Login Successful", Toast.LENGTH_SHORT)
				     .show();
				Intent pager = new Intent(v.getContext(), PagerActivity.class);
				startActivity(pager);
			}
		});
	}
}
