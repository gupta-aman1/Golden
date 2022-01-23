package com.example.goldenfish.sidebar.allReports;

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
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.goldenfish.sidebar.allReports.modelAllReports.AllReportNew;
import com.example.goldenfish.databinding.RowAllRechargeListBinding;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class AllReportAdapterNew extends RecyclerView.Adapter<AllReportAdapterNew.ViewHolder> {
    Context context;
    private ArrayList<AllReportNew> filteredData = null;
    SimpleDateFormat sourceFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
    // SimpleDateFormat destFormat = new SimpleDateFormat("dd MMM yyyy hh:mm:ss a"); //here 'a' for AM/PM
    SimpleDateFormat destFormat = new SimpleDateFormat("dd MMM yyyy, hh:mm a");

    public AllReportAdapterNew(Activity context, ArrayList<AllReportNew> data, String service_id) {
        this.context = context;
        this.filteredData = data;
        // System.out.println("FULL RESPONSE "+filteredData);
    }

    @Override
    public AllReportAdapterNew.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
//        View listItem = layoutInflater.inflate(R.layout.row_all_recharge_list, parent, false);
//        AllReportAdapterNew.ViewHolder viewHolder = new AllReportAdapterNew.ViewHolder(listItem);
//        return viewHolder;
        RowAllRechargeListBinding binding = RowAllRechargeListBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new AllReportAdapterNew.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull AllReportAdapterNew.ViewHolder holder, int position) {
        AllReportNew myListData = filteredData.get(position);

        holder.rowAllRechargeListBinding.aepsOrderId.setText(filteredData.get(position).getOrderID());
        holder.rowAllRechargeListBinding.aepsOperatorName.setText(filteredData.get(position).getOperatorName());
        holder.rowAllRechargeListBinding.aepsAadharNumber.setText(filteredData.get(position).getAadharNumber());
        holder.rowAllRechargeListBinding.aepsMobileNo.setText(filteredData.get(position).getMobileNo());
        holder.rowAllRechargeListBinding.aepsBankName.setText(filteredData.get(position).getBankName());
        holder.rowAllRechargeListBinding.aepsAmount.setText(filteredData.get(position).getAmount());
        holder.rowAllRechargeListBinding.aepsCommission.setText(filteredData.get(position).getCommission());
        holder.rowAllRechargeListBinding.aepsTotalTxnAmount.setText(filteredData.get(position).getTotalTxnAmount());
        holder.rowAllRechargeListBinding.aepsStatus.setText(filteredData.get(position).getStatus());
        holder.rowAllRechargeListBinding.aepsTime.setText(filteredData.get(position).getDateTime());
//        holder.tv_all_report_transaction_id.setText(filteredData.get(position).getTransactionid());
//        holder.tv_all_report_operator_name.setText(filteredData.get(position).getOpname());
//        holder.tv_all_report_number.setText(filteredData.get(position).getNumber());
//        holder.tv_all_report_amount.setText(filteredData.get(position).getAmount());
//        holder.tv_all_report_commission.setText(filteredData.get(position).getComm());
//        holder.tv_all_report_cost.setText(filteredData.get(position).getCost());
//        holder.tv_all_report_balance.setText(filteredData.get(position).getBalance());
//        holder.tv_all_report_date_time.setText(filteredData.get(position).getTdatetime());
//        holder.tv_all_report_status.setText(filteredData.get(position).getStatus());
//
//        Date date = null;
//        try {
//            date = sourceFormat.parse(filteredData.get(position).getTdatetime());
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        String formattedDate = destFormat.format(date);
//
//        holder.tv_all_report_date_time.setText(formattedDate.replace("am", "AM").replace("pm", "PM"));
//
//        holder.share.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                holder.bottom_ll.setVisibility(View.GONE);
//                Bitmap bitmap = getBitmapFromView(holder.main_ll, holder.main_ll.getChildAt(0).getHeight(), holder.main_ll.getChildAt(0).getWidth());
//                savetoGallery(bitmap, holder.bottom_ll);
//
//            }
//        });


        holder.rowAllRechargeListBinding.share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.bottom_ll.setVisibility(View.GONE);
                Bitmap bitmap = getBitmapFromView(holder.rowAllRechargeListBinding.mainLl, holder.rowAllRechargeListBinding.mainLl.getChildAt(0).getHeight(), holder.rowAllRechargeListBinding.mainLl.getChildAt(0).getWidth());
                savetoGallery(bitmap, holder.bottom_ll);
            }
        });
    }

    private Bitmap getBitmapFromView(View view, int height, int width) {
        // shareReceipt.setVisibility(View.GONE);
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
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

        RowAllRechargeListBinding rowAllRechargeListBinding;
       // TextView tv_all_report_transaction_id, tv_all_report_operator_name, tv_all_report_number, tv_all_report_amount,
         //       tv_all_report_commission, tv_all_report_cost, tv_all_report_balance, tv_all_report_date_time, tv_all_report_status;

        //Button share;
      //  ConstraintLayout main_ll;
        RelativeLayout bottom_ll;

        public ViewHolder(RowAllRechargeListBinding binding) {
            super(binding.getRoot());
            this.rowAllRechargeListBinding = binding;
        }
       /* public ViewHolder(@NonNull View itemView) {
            super(itemView);

           // book_time_bus = itemView.findViewById(R.id.book_time_bus);
            tv_all_report_transaction_id = itemView.findViewById(R.id.tv_all_report_transaction_id);
            tv_all_report_operator_name = itemView.findViewById(R.id.tv_all_report_operator_name);
            tv_all_report_number = itemView.findViewById(R.id.tv_all_report_number);
            tv_all_report_amount = itemView.findViewById(R.id.tv_all_report_amount);
            tv_all_report_commission = itemView.findViewById(R.id.tv_all_report_commission);
            tv_all_report_cost = itemView.findViewById(R.id.tv_all_report_cost);
            tv_all_report_balance = itemView.findViewById(R.id.tv_all_report_balance);
            tv_all_report_date_time = itemView.findViewById(R.id.tv_all_report_date_time);
            tv_all_report_status = itemView.findViewById(R.id.tv_all_report_status);
            share=itemView.findViewById(R.id.share);
            main_ll=itemView.findViewById(R.id.main_ll);
            bottom_ll=itemView.findViewById(R.id.bottom_ll);
        }
    }*/
    }
}
