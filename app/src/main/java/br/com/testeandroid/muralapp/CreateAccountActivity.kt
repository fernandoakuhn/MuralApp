//
package br.com.testeandroid.muralapp

import android.content.Intent
import android.nfc.Tag
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.text.TextUtils.*
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_create_account.*
import kotlin.math.log

class CreateAccountActivity : AppCompatActivity() {


    //elementos ui
    private var etFirstName: EditText? = null
    private var etLastName: EditText? = null
    private var etEmail: EditText? = null
    private var etPassword: EditText? = null
    private var btnCreateAccount: Button? = null
    private var mProgressBar: ProgressBar? = null

    //reeferencia do bd
    private var mDatabaseReference: DatabaseReference? = null
    private var mDatabase: FirebaseDatabase? = null
    private var mAuth: FirebaseAuth? = null

    private val TAG = "CreateAccountActivity"

    //variaveis globais
    private var firstName: String? = null
    private var lastName: String? = null
    private var email: String? = null
    private var password: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_account)

        initialise()
    }

    //inicializa campos, passando valor dos texts para variaveis
    private fun initialise() {
        etFirstName = findViewById(R.id.et_first_name) as EditText
        etLastName = findViewById(R.id.et_last_name) as EditText
        etEmail = findViewById(R.id.et_email) as EditText
        etPassword = findViewById(R.id.et_password) as EditText
        btnCreateAccount = findViewById(R.id.btn_register) as Button
        // mProgressBar     = ProgressBar(this)

        mDatabase = FirebaseDatabase.getInstance()
        mDatabaseReference = mDatabase!!.reference!!.child("Users")
        mAuth = FirebaseAuth.getInstance()
        //evento de click
        btnCreateAccount!!.setOnClickListener { createNewAccount() }
    }

    //funcao privada para criar a conta
    private fun createNewAccount() {
        firstName = etFirstName?.text.toString()
        lastName = etLastName?.text.toString()
        email = etEmail?.text.toString()
        password = etPassword?.text.toString()
        //verifica campos nulos
        if (!isEmpty(firstName) && !isEmpty(lastName) && !isEmpty(email) && !isEmpty(password)) {
            Toast.makeText(this, "Campos preenchidos.", Toast.LENGTH_SHORT)
        } else {
            Toast.makeText(this, "Por favor, preencha os campos.", Toast.LENGTH_SHORT)
        }
        //mProgressBar!!.visibility = ProgressBar.VISIBLE
        //declara valores para autenticação do firebase e valida email
        mAuth!!.createUserWithEmailAndPassword(email!!, password!!)
            .addOnCompleteListener(this) { task ->
                //mProgressBar!!.visibility = ProgressBar.GONE
                if (task.isSuccessful) {
                    Log.d(TAG, "createUserWithEmail:success")
                    val userId = mAuth!!.currentUser!!.uid
                    verifyEmail()
                    val currentUserDb = mDatabaseReference!!.child(userId)
                    currentUserDb.child("firstName").setValue(firstName)
                    currentUserDb.child("lastName").setValue(lastName)
                    updateUserInfoAndUI()
                } else {
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    Toast.makeText(
                        this@CreateAccountActivity, "Falha na autenticação.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

    }

    //atualiza informaçoes no bd
    private fun updateUserInfoAndUI() {
        val intent = Intent(this@CreateAccountActivity, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }

    //verifica email
    private fun verifyEmail() {
        val mUser = mAuth!!.currentUser;
        mUser!!.sendEmailVerification()
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(
                        this@CreateAccountActivity,
                        "E-mail de verificação enviado para " + mUser.getEmail(),
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Log.e(TAG, "sendEmailVerification", task.exception)
                    Toast.makeText(
                        this@CreateAccountActivity,
                        "Falha ao enviar e-mail de verificação.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }

}
