/*
 * Copyright (C) 2011-2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/*
 * This file is auto-generated. DO NOT MODIFY!
 * The source Renderscript file: /autofs/unitytravail/travail/aaneas/ProjetTechnologique/app/src/main/rs/colorkeep.rs
 */

package com.example.aaneas.afficherimagenew;

import android.os.Build;
import android.os.Process;
import java.lang.reflect.Field;
import android.renderscript.*;
import android.content.res.Resources;

/**
 * @hide
 */
public class ScriptC_colorkeep extends ScriptC {
    private static final String __rs_resource_name = "colorkeep";
    // Constructor
    public  ScriptC_colorkeep(RenderScript rs) {
        this(rs,
             rs.getApplicationContext().getResources(),
             rs.getApplicationContext().getResources().getIdentifier(
                 __rs_resource_name, "raw",
                 rs.getApplicationContext().getPackageName()));
    }

    public  ScriptC_colorkeep(RenderScript rs, Resources resources, int id) {
        super(rs, resources, id);
        __F64 = Element.F64(rs);
        __U8_4 = Element.U8_4(rs);
    }

    private Element __F64;
    private Element __U8_4;
    private FieldPacker __rs_fp_F64;
    private final static int mExportVarIdx_rand = 0;
    private double mExportVar_rand;
    public synchronized void set_rand(double v) {
        setVar(mExportVarIdx_rand, v);
        mExportVar_rand = v;
    }

    public double get_rand() {
        return mExportVar_rand;
    }

    public Script.FieldID getFieldID_rand() {
        return createFieldID(mExportVarIdx_rand, null);
    }

    //private final static int mExportForEachIdx_root = 0;
    private final static int mExportForEachIdx_colorkeep = 1;
    public Script.KernelID getKernelID_colorkeep() {
        return createKernelID(mExportForEachIdx_colorkeep, 35, null, null);
    }

    public void forEach_colorkeep(Allocation ain, Allocation aout) {
        forEach_colorkeep(ain, aout, null);
    }

    public void forEach_colorkeep(Allocation ain, Allocation aout, Script.LaunchOptions sc) {
        // check ain
        if (!ain.getType().getElement().isCompatible(__U8_4)) {
            throw new RSRuntimeException("Type mismatch with U8_4!");
        }
        // check aout
        if (!aout.getType().getElement().isCompatible(__U8_4)) {
            throw new RSRuntimeException("Type mismatch with U8_4!");
        }
        Type t0, t1;        // Verify dimensions
        t0 = ain.getType();
        t1 = aout.getType();
        if ((t0.getCount() != t1.getCount()) ||
            (t0.getX() != t1.getX()) ||
            (t0.getY() != t1.getY()) ||
            (t0.getZ() != t1.getZ()) ||
            (t0.hasFaces()   != t1.hasFaces()) ||
            (t0.hasMipmaps() != t1.hasMipmaps())) {
            throw new RSRuntimeException("Dimension mismatch between parameters ain and aout!");
        }

        forEach(mExportForEachIdx_colorkeep, ain, aout, null, sc);
    }

}

