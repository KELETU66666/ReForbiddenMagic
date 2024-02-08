package keletu.forbiddenmagicre.blocks.tiles;


import keletu.forbiddenmagicre.ConfigFM;
import keletu.forbiddenmagicre.util.RegistryHandler;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import thaumcraft.api.ThaumcraftApiHelper;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.aspects.IAspectContainer;
import thaumcraft.api.aspects.IEssentiaTransport;


public class TileEntityWrathCage extends TileEntity implements IAspectContainer, IEssentiaTransport, ITickable
{
    private final WrathSpawnerLogic spawnLogic = new WrathSpawnerLogic(this);
    public int wrath = 0;
    public int sloth = 0;
    public int special = 0;
    public byte mode = 0;
    Aspect aspect = Aspect.DESIRE;

    @Override
    /**
     * Reads a tile entity from NBT.
     */
    public void readFromNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.readFromNBT(par1NBTTagCompound);
        this.spawnLogic.readFromNBT(par1NBTTagCompound);
        this.wrath = par1NBTTagCompound.getShort("Wrath");
        this.sloth = par1NBTTagCompound.getShort("Sloth");
        this.special = par1NBTTagCompound.getShort("Special");
        this.mode = par1NBTTagCompound.getByte("Mode");
        aspect = spawnLogic.getAspect();
    }

    @Override
    /**
     * Writes a tile entity to NBT.
     */
    public NBTTagCompound writeToNBT(NBTTagCompound par1NBTTagCompound)
    {
        super.writeToNBT(par1NBTTagCompound);
        this.spawnLogic.writeToNBT(par1NBTTagCompound);
        par1NBTTagCompound.setShort("Wrath", (short)this.wrath);
        par1NBTTagCompound.setShort("Sloth", (short)this.sloth);
        par1NBTTagCompound.setShort("Special", (short)this.special);
        par1NBTTagCompound.setByte("Mode", this.mode);
        return par1NBTTagCompound;
    }

    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
        readFromNBT(pkt.getNbtCompound());
        IBlockState iblockstate = world.getBlockState(pos);
        world.notifyBlockUpdate(pos, iblockstate, iblockstate, 0);
    }

    @Override
    public SPacketUpdateTileEntity getUpdatePacket()  {
        NBTTagCompound nbttagcompound = new NBTTagCompound();
        this.writeToNBT(nbttagcompound);
        return new SPacketUpdateTileEntity(pos, 0, nbttagcompound);
    }

    /**
     * Allows the entity to update its state. Overridden in most subclasses, e.g. the mob spawner uses this to count
     * ticks and creates a new spawn inside its implementation.
     */
    @Override
    public void update()
    {
        this.spawnLogic.updateSpawner();

        if(ConfigFM.wrathCost > 0 && spawnLogic.isMobSet() && !(special >= 64 || wrath >= 64 || sloth >= 64))
            drawEssentia();
    }

    void drawEssentia()
    {
        IBlockState iblockstate = world.getBlockState(pos);
        for(int x = 0; x < EnumFacing.byIndex(x).getIndex(); x++){
            EnumFacing current = EnumFacing.byIndex(x);
            TileEntity te = ThaumcraftApiHelper.getConnectableTile(world, pos, current);
            if(te != null) {
                IEssentiaTransport ic = (IEssentiaTransport)te;
                if(ic.canOutputTo(current.getOpposite()) && special < 64 //THE DIRECTION HERE MAY BE WRONG SPITEFULFOXY SO CHECK IT
                        && ic.getEssentiaType(current.getOpposite()) == aspect && ic.getEssentiaAmount(current.getOpposite()) > 0 && ic.takeEssentia(aspect, 1, current.getOpposite()) == 1) {
                    special++;
                    world.notifyBlockUpdate(pos, iblockstate, iblockstate, 0);
                    return;
                }
                else if(ic.canOutputTo(current.getOpposite()) && wrath < 64 && special < ConfigFM.wrathCost
                        && ic.getEssentiaType(current.getOpposite()) == RegistryHandler.WRATH && ic.getEssentiaAmount(current.getOpposite()) > 0 && ic.takeEssentia(RegistryHandler.WRATH, 1, current.getOpposite()) == 1) {
                    wrath++;
                    world.notifyBlockUpdate(pos, iblockstate, iblockstate, 0);
                    return;
                }
                else if(ic.canOutputTo(current.getOpposite()) && sloth < 64 && special < ConfigFM.wrathCost && wrath < ConfigFM.wrathCost
                        && ic.getEssentiaType(current.getOpposite()) == RegistryHandler.SLOTH && ic.getEssentiaAmount(current.getOpposite()) > 0 && ic.takeEssentia(RegistryHandler.SLOTH, 1, current.getOpposite()) == 1) {
                    sloth++;
                    world.notifyBlockUpdate(pos, iblockstate, iblockstate, 0);
                    return;
                }
            }
        }
    }

    public void checkAspect() {
        if(aspect != spawnLogic.getAspect())
            special = 0;
        aspect = spawnLogic.getAspect();
    }

    /**
     * Called when a client event is received with the event number and argument, see World.sendClientEvent
     */
    public boolean receiveClientEvent(int par1, int x)
    {
        return this.spawnLogic.setDelayToMin(par1) ? true : super.receiveClientEvent(par1, x);
    }

    /**
     * Returns the spawner logic associated with this spawner
     */
    public WrathSpawnerLogic getSpawnerLogic()
    {
        return this.spawnLogic;
    }

    public AspectList getAspects()
    {
        AspectList list = new AspectList();
        if(ConfigFM.wrathCost > 0 && spawnLogic.isMobSet())
        {
            if(mode == 0)
                list.add(aspect, special);
            else if(mode == 1)
                list.add(RegistryHandler.WRATH, wrath);
            else
                list.add(RegistryHandler.SLOTH, sloth);
        }
        return list;
    }

    public void setAspects(AspectList aspects)
    {

    }

    /**
     * This method is used to determine of a specific aspect can be added to this container.
     * @param tag
     * @return true or false
     */
    public boolean doesContainerAccept(Aspect tag)
    {
        if(ConfigFM.wrathCost <= 0)
            return false;
        else
            return (tag == RegistryHandler.WRATH || tag == RegistryHandler.SLOTH || tag == aspect);
    }

    public int addToContainer(Aspect tag, int amount)
    {
        if(ConfigFM.wrathCost <= 0)
            return amount;
        if(tag == RegistryHandler.WRATH){
            wrath += amount;
            return 0;
        }
        else if(tag == RegistryHandler.SLOTH){
            sloth += amount;
            return 0;
        }
        else if(tag == aspect){
            special += amount;
            return 0;
        }
        else
            return amount;
    }

    /**
     * Removes a certain amount of a specific aspect from the tile entity
     * @param tag
     * @param amount
     * @return true if that amount of aspect was available and was removed
     */
    public boolean takeFromContainer(Aspect tag, int amount)
    {
        return false;
    }

    /**
     * Checks if the tile entity contains the listed amount (or more) of the aspect
     * @param tag
     * @param amount
     * @return
     */
    public boolean doesContainerContainAmount(Aspect tag, int amount)
    {
        if(tag == RegistryHandler.WRATH)
            return wrath >= amount;
        else if(tag == RegistryHandler.SLOTH)
            return sloth >= amount;
        else if(tag == aspect)
            return special >= amount;
        else
            return false;
    }

    /**
     * Returns how much of the aspect this tile entity contains
     * @param tag
     * @return the amount of that aspect found
     */
    public int containerContains(Aspect tag)
    {
        if(tag == RegistryHandler.WRATH)
            return wrath;
        else if(tag == RegistryHandler.SLOTH)
            return sloth;
        else if(tag == aspect)
            return special;
        else
            return 0;
    }

    /**
     * Checks if the tile entity contains all the listed aspects and their amounts
     * @param ot the ObjectTags object that contains the aspects and their amounts.
     * @return
     *
     * Going away in the next major patch
     */
    @Deprecated
    public boolean doesContainerContain(AspectList ot)
    {
        return false;
    }

    /**
     * removes a bunch of different aspects and amounts from the tile entity.
     * @param ot the ObjectTags object that contains the aspects and their amounts.
     * @return true if all the aspects and their amounts were available and successfully removed
     *
     * Going away in the next major patch
     */
    @Deprecated
    public boolean takeFromContainer(AspectList ot)
    {
        return false;
    }

    /**
     * Is this tile able to connect to other vis users/sources on the specified side?
     * @param face
     * @return
     */
    public boolean isConnectable(EnumFacing face)
    {
        return ConfigFM.wrathCost > 0;
    }

    /**
     * Is this side used to input essentia?
     * @param face
     * @return
     */
    public boolean canInputFrom(EnumFacing face)
    {
        return true;
    }

    /**
     * Is this side used to output essentia?
     * @param face
     * @return
     */
    public boolean canOutputTo(EnumFacing face)
    {
        return false;
    }

    /**
     * Sets the amount of suction this block will apply
     */
    public void setSuction(Aspect aspect, int amount)
    {

    }

    //**
    //* Returns the amount of suction this block is applying.
    //* @param loc
    //*         the location from where the suction is being checked
    //* @return
    //*/
    //public AspectList getSuction(EnumFacing face)
    //{
    //AspectList list = new AspectList();
    //if(ConfigFM.wrathCost <= 0)
    //return list;
    //if(special < 64)
    //list.add(aspect, 256);
    //if(special < ConfigFM.wrathCost && wrath < 64)
    //list.add(RegistryHandler.WRATH, 256);
    //if(special < ConfigFM.wrathCost && wrath < ConfigFM.wrathCost && sloth < 64)
    //list.add(RegistryHandler.SLOTH, 256);

    //return list;
    //}

    /**
     * Returns the type of suction this block is applying.
     * @return
     *         a return type of null indicates the suction is untyped and the first thing available will be drawn
     */
    public Aspect getSuctionType(EnumFacing face)
    {
        if(ConfigFM.wrathCost <= 0)
            return null;
        if(mode == 0)
            return aspect;
        else if(mode == 1)
            return RegistryHandler.WRATH;
        else
            return RegistryHandler.SLOTH;
    }

    /**
     * Returns the strength of suction this block is applying.
     *         the location from where the suction is being checked
     * @return
     */
    public int getSuctionAmount(EnumFacing face)
    {
        if(ConfigFM.wrathCost > 0)
            return 256;
        else
            return 0;
    }

    /**
     * remove the specified amount of vis from this transport tile
     * @return how much was actually taken
     */
    public int takeEssentia(Aspect aspect, int amount, EnumFacing dir)
    {
        return 0;
    }

    /**
     * add the specified amount of vis to this transport tile
     * @return how much was actually added
     */
    public int addEssentia(Aspect inp, int amount, EnumFacing dir)
    {
        if(inp == aspect)
        {
            special += Math.min(amount, 64);
            return Math.min(amount, 64);
        }
        else if(inp == RegistryHandler.WRATH)
        {
            wrath += Math.min(amount, 64);
            return Math.min(amount, 64);
        }
        else if(inp == RegistryHandler.SLOTH)
        {
            sloth += Math.min(amount, 64);
            return Math.min(amount, 64);
        }
        else
            return amount;
    }

    /**
     * What type of essentia this contains
     * @param face
     * @return
     */
    public Aspect getEssentiaType(EnumFacing face)
    {
        return getSuctionType(face);
    }

    /**
     * How much essentia this block contains
     * @param face
     * @return
     */
    public int getEssentiaAmount(EnumFacing face)
    {
        if(mode == 0)
            return special;
        else if(mode == 1)
            return wrath;
        else
            return sloth;
    }

    /**
     * Essentia will not be drawn from this container unless the suction exceeds this amount.
     * @return the amount
     */
    public int getMinimumSuction()
    {
        return 9000;
    }

    /**
     * Return true if you want the conduit to extend a little further into the block.
     * Used by jars and alembics that have smaller than normal hitboxes
     * @return
     */
    public boolean renderExtendedTube()
    {
        return false;
    }
}
