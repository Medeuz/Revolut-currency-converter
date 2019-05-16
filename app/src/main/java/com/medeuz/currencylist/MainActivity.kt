package com.medeuz.currencylist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.medeuz.currencylist.ui.CurrencyAdapter
import com.medeuz.currencylist.ui.CurrencyDecorator
import com.medeuz.currencylist.ui.CurrencyVO
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), CurrencyListView {

    private val compositeDisposable = CompositeDisposable()
    private val presenter = CurrencyListPresenter(this)
    private val currencyAdapter = CurrencyAdapter().apply {
        onClickListener = {
            compositeDisposable.clear()
            presenter.loadCurrencyList(CurrencyListView.CURRENCY_UPDATE_DELAY_SECONDS, it)
                .apply { compositeDisposable.add(this) }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        currencyRv.addItemDecoration(CurrencyDecorator())
        currencyRv.adapter = currencyAdapter
        currencyRv.layoutManager = LinearLayoutManager(this)
    }

    override fun onResume() = super.onResume().also {
        presenter.loadCurrencyList(CurrencyListView.CURRENCY_UPDATE_DELAY_SECONDS).also {
            compositeDisposable.add(it)
        }
    }

    override fun onPause() = super.onPause().also { compositeDisposable.clear() }

    override fun showCurrencyList(items: List<CurrencyVO>) {
        currencyAdapter.updateItems(items)
    }

    override fun showError() = Toast.makeText(this, R.string.default_error, Toast.LENGTH_LONG).show()

    override fun showLoading() {
        currencyRv.visibility = View.GONE
        progressBar.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        currencyRv.visibility = View.VISIBLE
        progressBar.visibility = View.GONE
    }
}
