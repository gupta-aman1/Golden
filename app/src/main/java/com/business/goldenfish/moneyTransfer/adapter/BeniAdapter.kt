package com.business.goldenfish.moneyTransfer.adapter

import android.view.View
import com.business.goldenfish.R
import com.business.goldenfish.databinding.BenificiaryItemBinding
import com.business.goldenfish.moneyTransfer.modeldmt.Beneficiary

class BeniAdapter : BaseRecyclerViewAdapter<Beneficiary,BenificiaryItemBinding>() {

    override fun getLayout() = R.layout.benificiary_item

    override fun onBindViewHolder(holder: Companion.BaseViewHolder<BenificiaryItemBinding>, position: Int) {
        holder.binding.beni = items[position]

        if(items.get(position).status.equals("0"))
        {
            holder.binding.verifiedBtn.visibility= View.GONE
        }
        holder.binding.verifiedBtn.setOnClickListener {
            listener?.invoke(it, items[position], position)
        }
        holder.binding.payNow.setOnClickListener {
            listener?.invoke(it, items[position], position)
        }
        holder.binding.deleteNow.setOnClickListener {
            listener?.invoke(it, items[position], position)
        }
        holder.binding.root.setOnClickListener {
            listener?.invoke(it, items[position], position)
        }
    }
}