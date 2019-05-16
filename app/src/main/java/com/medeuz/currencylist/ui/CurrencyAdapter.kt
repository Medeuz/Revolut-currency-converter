package com.medeuz.currencylist.ui

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.medeuz.currencylist.R
import kotlinx.android.synthetic.main.item_currency_list.view.*
import java.text.DecimalFormat

class CurrencyAdapter : RecyclerView.Adapter<CurrencyAdapter.CurrencyViewHolder>() {
    private val numberFormat = DecimalFormat("#0.00")
    private var items: List<CurrencyVO> = emptyList()
    private var amount = 1.0

    var onClickListener: ((String) -> Unit)? = null

    fun updateItems(newItems: List<CurrencyVO>) {
        val diffResult = DiffUtil.calculateDiff(DiffCallback(items, newItems))
        items = newItems
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyViewHolder =
        LayoutInflater.from(parent.context).inflate(R.layout.item_currency_list, parent, false).run {
            CurrencyViewHolder(this)
        }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: CurrencyViewHolder, position: Int) = holder.bind(items[position])

    override fun onBindViewHolder(holder: CurrencyViewHolder, position: Int, payloads: MutableList<Any>) {
        payloads.takeIf { it.isNotEmpty() }?.firstOrNull()?.let { holder.bind(it as Double) }
            ?: holder.bind(items[position])
    }

    inner class CurrencyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(item: CurrencyVO) = with(itemView) {
            currencyIv.setImageResource(item.image)
            currencyCodeTv.text = item.code
            currencyTitleTv.text = item.title
            currencyValueEt.setText(numberFormat.format(item.value * amount))
            currencyValueEt.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {

                }

                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    if (currencyValueEt.isFocused) {
                        amount = s.toString().toDouble()
                    }
                }

            })
            currencyValueEt.setOnFocusChangeListener { _, hasFocus ->
                if (hasFocus) {
                    if (layoutPosition > 0) {
                        onClickListener?.invoke(items[adapterPosition].code)
                    }
                }
            }
        }

        fun bind(payload: Double) =
            itemView.currencyValueEt.takeIf { !it.isFocused }?.apply { setText(numberFormat.format(payload * amount)) }
    }
}

class DiffCallback(
    private val oldList: List<CurrencyVO>,
    private val newList: List<CurrencyVO>
) : DiffUtil.Callback() {
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition].code == newList[newItemPosition].code

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition] == newList[newItemPosition]

    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Double = newList[newItemPosition].value
}