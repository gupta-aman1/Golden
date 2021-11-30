package com.example.goldenfish.ledgerreopt;


import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.goldenfish.Constants.ConstantsValue;
import com.example.goldenfish.databinding.RowAllRechargeListBinding;
import com.example.goldenfish.databinding.RowLedgerBinding;
import com.example.goldenfish.sidebar.allReports.modelAllReports.AllReportNew;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Adapterledger extends RecyclerView.Adapter<Adapterledger.ViewHolder> {
    Context context;
    private ArrayList<ModelDataLedger> filteredData = null;

    public Adapterledger(Activity context, ArrayList<ModelDataLedger> data) {
        this.context = context;
        this.filteredData = data;
       // this.service_id = service_id;
        // System.out.println("FULL RESPONSE "+filteredData);
    }

    @Override
    public Adapterledger.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
//        View listItem = layoutInflater.inflate(R.layout.row_all_recharge_list, parent, false);
//        AllReportAdapterNew.ViewHolder viewHolder = new AllReportAdapterNew.ViewHolder(listItem);
//        return viewHolder;
        RowLedgerBinding binding = RowLedgerBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new Adapterledger.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapterledger.ViewHolder holder, int position) {
        ModelDataLedger myListData = filteredData.get(position);

        holder.rowAllRechargeListBinding.balanceId.setText(String.valueOf(filteredData.get(position).getBalanceId()));
        holder.rowAllRechargeListBinding.orderId.setText(filteredData.get(position).getOrderId());
        holder.rowAllRechargeListBinding.serviceName.setText(filteredData.get(position).getServiceName());
        holder.rowAllRechargeListBinding.refId.setText(String.valueOf(filteredData.get(position).getRefrenceId()));
        holder.rowAllRechargeListBinding.account.setText(filteredData.get(position).getAccount());
        holder.rowAllRechargeListBinding.orderValue.setText(String.valueOf(filteredData.get(position).getOrderValue()));
        holder.rowAllRechargeListBinding.amount.setText(String.valueOf(filteredData.get(position).getAmount()));
        holder.rowAllRechargeListBinding.commission.setText(String.valueOf(filteredData.get(position).getCommission()));
        holder.rowAllRechargeListBinding.surcharge.setText(String.valueOf(filteredData.get(position).getSurcharge()));
        holder.rowAllRechargeListBinding.crdr.setText(filteredData.get(position).getCrDr());
        holder.rowAllRechargeListBinding.status.setText(filteredData.get(position).getStatus());
        holder.rowAllRechargeListBinding.openbal.setText(String.valueOf(filteredData.get(position).getOpeningBal()));
        holder.rowAllRechargeListBinding.closebal.setText(String.valueOf(filteredData.get(position).getClosingBal()));
        holder.rowAllRechargeListBinding.remark.setText(filteredData.get(position).getRemarks());



        SimpleDateFormat sourceFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        // SimpleDateFormat destFormat = new SimpleDateFormat("dd MMM yyyy hh:mm:ss a"); //here 'a' for AM/PM
        SimpleDateFormat destFormat = new SimpleDateFormat("dd MMM yyyy, hh:mm a");

        Date date = null;
        try {
            date = sourceFormat.parse(filteredData.get(position).getDateTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String formattedDate = destFormat.format(date);

        holder.rowAllRechargeListBinding.datetime.setText(formattedDate);


        holder.rowAllRechargeListBinding.mainShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.rowAllRechargeListBinding.bottomLayout.setVisibility(View.GONE);
                Bitmap bitmap = getBitmapFromView(holder.rowAllRechargeListBinding.mainLl, holder.rowAllRechargeListBinding.mainLl.getChildAt(0).getHeight(), holder.rowAllRechargeListBinding.mainLl.getChildAt(0).getWidth());
                savetoGallery(bitmap, holder.rowAllRechargeListBinding.bottomLayout);
            }
        });
    }

    private Bitmap getBitmapFromView(View view, int height, int width) {

        int spec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        view.measure(spec, spec);
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        // shareReceipt.setVisibility(View.GONE);
        Bitmap bitmap = Bitmap.createBitmap(view.getMeasuredWidth(), view.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Drawable bgDrawable = view.getBackground();
        if (bgDrawable != null)
            bgDrawable.draw(canvas);
        else
            canvas.drawColor(Color.WHITE);
        view.draw(canvas);
        return bitmap;
    }

    private void savetoGallery(Bitmap bitmap, RelativeLayout relativeLayout) {
        //shareReceipt.setVisibility(View.VISIBLE);
        relativeLayout.setVisibility(View.VISIBLE);
        String root = Environment.getExternalStorageDirectory().getPath() + "/Golden/";
        File myDir = new File(root);
        if (!myDir.exists()) {
            myDir.mkdirs();
        }
        String fname = "Transaction" + System.currentTimeMillis() + ".jpg";
        File myfile = new File(myDir, fname);

        try {
            FileOutputStream out = new FileOutputStream(myfile);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        //Log.e("jjh", String.valueOf(myfile));
        MediaScannerConnection.scanFile(context, new String[]{myfile.toString()}, null,
                new MediaScannerConnection.OnScanCompletedListener() {
                    public void onScanCompleted(String path, Uri uri) {
                        //Log.i("ExternalStorage", "Scanned " + path + ":");
                        //Log.i("ExternalStorage", "-> uri=" + uri);
                    }
                });

        try {

            ContentValues contentValues = new ContentValues();

            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
            String path = MediaStore.Images.Media.insertImage(context.getContentResolver(), bitmap, String.valueOf(System.currentTimeMillis()), null);
            Uri imageUri = Uri.parse(path);
            Intent share = new Intent(Intent.ACTION_SEND);
            share.setType("image/*");
            share.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
            share.putExtra(Intent.EXTRA_STREAM, imageUri);
            context.startActivity(Intent.createChooser(share, "Share link!"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return filteredData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        RowLedgerBinding rowAllRechargeListBinding;
        // TextView tv_all_report_transaction_id, tv_all_report_operator_name, tv_all_report_number, tv_all_report_amount,
        //        tv_all_report_commission, tv_all_report_cost, tv_all_report_balance, tv_all_report_date_time, tv_all_report_status;

        //  Button share;
        //  ConstraintLayout main_ll;
        // RelativeLayout bottom_ll;

        public ViewHolder(RowLedgerBinding binding) {
            super(binding.getRoot());
            this.rowAllRechargeListBinding = binding;
        }

    }
}
