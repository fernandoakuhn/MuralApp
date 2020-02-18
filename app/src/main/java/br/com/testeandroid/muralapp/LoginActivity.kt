package br.com.testeandroid.muralapp

import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.*
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private val TAG = "LoginActivity"

    //variaveis globais
    private var email: String? = null
    private var password: String? = null
    private var remember: Boolean? = null
    //elementos ui
    private var tvForgotPassword: TextView? = null
    private var etEmail: TextView? = null
    private var etPassword: TextView? = null
    private var btnLogin: Button? = null
    private var btnCreateAccount: Button? = null
    private var chkRemember: CheckBox? = null
    //private var mProgressBar: ProgressBar? = null

    //referencia bd
    private var mAuth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        initialise()
    }

    private fun initialise() {
        val sharedPreferences = getSharedPreferences("SP_INFO", Context.MODE_PRIVATE)
        tvForgotPassword = findViewById(R.id.tv_forgot_password) as TextView
        etEmail = findViewById(R.id.et_email) as EditText
        etPassword = findViewById(R.id.et_password) as EditText
        btnLogin = findViewById(R.id.btn_login) as Button
        btnCreateAccount = findViewById(R.id.btn_register_account) as Button
        chkRemember = findViewById(R.id.chk_remember) as CheckBox
        email = sharedPreferences.getString("EMAIL", etEmail?.text.toString())
        password = sharedPreferences.getString("PWD", etPassword?.text.toString())
        remember = sharedPreferences.getBoolean("SP", false)
        etEmail?.text = email
        etPassword?.text = password
        chkRemember?.isChecked = remember as Boolean


        //mProgressBar!!.visibility = ProgressBar.VISIBLE
        mAuth = FirebaseAuth.getInstance()
        tvForgotPassword!!
            .setOnClickListener {
                startActivity(
                    Intent(
                        this@LoginActivity,
                        ForgotPasswordActivity::class.java
                    )
                )
            }
        btnCreateAccount!!
            .setOnClickListener {
                startActivity(
                    Intent(
                        this@LoginActivity,
                        CreateAccountActivity::class.java
                    )
                )
            }
        btnLogin!!.setOnClickListener { loginUser() }
        //mProgressBar!!.visibility = ProgressBar.GONE

    }

    private fun loginUser() {
        val sharedPreferences = getSharedPreferences("SP_INFO", Context.MODE_PRIVATE)
        email = etEmail?.text.toString()
        password = etPassword?.text.toString()
        remember = chkRemember?.isChecked
        if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {
            // mProgressBar!!.visibility = ProgressBar.VISIBLE
            Log.d(TAG, "Logging in user.")
            mAuth!!.signInWithEmailAndPassword(email!!, password!!)
                .addOnCompleteListener(this) { task ->
                    if (chkRemember!!.isChecked) {
                        val sharedPrefer = sharedPreferences.edit()
                        sharedPrefer.putString("EMAIL", email)
                        sharedPrefer.putString("PWD", password)
                        sharedPrefer.putBoolean("SP", remember as Boolean)
                        sharedPrefer.apply()
                    } else {
                        val sharedPrefer = sharedPreferences.edit()
                        sharedPrefer.putString("EMAIL", "")
                        sharedPrefer.putString("PWD", "")
                        sharedPrefer.putBoolean("SP", false)
                        sharedPrefer.apply()
                    }
                    if (task.isSuccessful) {
                        // Sign in success, update UI with signed-in user's information
                        Log.d(TAG, "signInWithEmail:success")
                        updateUI()
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.e(TAG, "signInWithEmail:failure", task.exception)
                        Toast.makeText(
                            this@LoginActivity, "Falha na autenticação.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
        } else {
            Toast.makeText(this, "Informe todos os dados.", Toast.LENGTH_SHORT).show()
        }

    }

    private fun updateUI() {
        val intent = Intent(this@LoginActivity, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }

}

