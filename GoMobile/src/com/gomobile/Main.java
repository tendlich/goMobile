package com.gomobile;

import android.content.Intent;
import android.os.Bundle;

import com.gomobile.navigation.ViewWithNavigation;
import com.gomobile.technicalservices.BarcodeScanner;
import com.gomobile.technicalservices.DataConnectionActivity;

public class Main extends ViewWithNavigation {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		Intent intent = new Intent(this, DataConnectionActivity.class);
		startActivity(intent);
	}

	public void startScanner() {
		Intent intent = new Intent(this, BarcodeScanner.class);
		startActivity(intent);
	}

	public void navigateRight() {
		startScanner();
	}

	@Override
	public void navigateLeft() {
		// TODO Auto-generated method stub

	}

	@Override
	public void navigateUp() {
		// TODO Auto-generated method stub

	}

	@Override
	public void navigateDown() {
		// TODO Auto-generated method stub
	}

}
