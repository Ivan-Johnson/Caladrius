package edu.ua.cs.cs495.caladrius.android;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import edu.ua.cs.cs495.caladrius.User;
import edu.ua.cs.cs495.caladrius.fitbit.FitbitAccount;
import edu.ua.cs.cs495.caladrius.server.ServerAccount;

public class LoginScreen extends AppCompatActivity
{
	protected void login(Context cntxt, @NonNull User u)
	{
		Caladrius.user = u;

		Intent pager = new Intent(cntxt, PagerActivity.class);
		startActivity(pager);
	}
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		// TODO check if the user is already logged in

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

				FitbitAccount fAcc;
				try {
					fAcc = new FitbitAccount(user, pass);
				} catch (IllegalArgumentException ex) {
					Toast.makeText(v.getContext(), "Login Failed", Toast.LENGTH_SHORT).show();
					return;
				}
				ServerAccount sAcc = new ServerAccount();

				User u = new User(fAcc, sAcc);

				login(v.getContext(), u);

				Toast.makeText(v.getContext(), "Login Successful", Toast.LENGTH_SHORT).show();
			}
		});
	}
}
