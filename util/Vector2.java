package gridwhack.util;

/**
 * 2-Dimensional vector class file.
 * This class provides basic vector mathematics.
 * @author Christoffer Niska <ChristofferNiska@gmail.com>
 */
public final class Vector2
{
	// ----------
	// Properties
	// ----------

	public float x;
	public float y;

	// -------
	// Methods
	// -------

	/**
	 * Creates the vector.
	 */
	public Vector2()
	{
		zero();
	}

	/**
	 * Creates the vector.
	 * @param x The value on the x-axis.
	 * @param y The value on the y-axis.
	 */
	public Vector2(float x, float y)
	{
		this();
		set(x, y);
	}

	/**
	 * Creates the vector based on another vector.
	 * @param other The other vector.
	 */
	public Vector2(Vector2 other)
	{
		this();
		set(other);
	}

	/**
	 * Adds to this vector.
	 * @param x The value to add to the x-axis.
	 * @param y The value to add to the y-axis.
	 */
	public final void add(float x, float y)
	{
		this.x += x;
		this.y += y;
	}

	/**
	 * Sums another vector to this vector.
	 * @param other The other vector.
	 */
	public final void add(Vector2 other)
	{
		this.x += other.x;
		this.y += other.y;
	}

	/**
	 * Substracts another vector from this vector.
	 * @param other The other vector.
	 */
	public final void substract(Vector2 other)
	{
		this.x -= other.x;
		this.y -= other.y;
	}

	/**
	 * Substracts from this vector.
	 * @param x The value to substract from the x-axis.
	 * @param y The value to substract from the y-axis.
	 */
	public final void substract(float x, float y)
	{
		this.x -= x;
		this.y -= y;
	}

	/**
	 * Multiplies this vector.
	 * @param multiplier The multiplier.
	 */
	public final void multiply(float multiplier)
	{
		this.x *= multiplier;
		this.y *= multiplier;
	}

	/**
	 * Multiplies this vector with another vector.
	 * @param other The other vector.
	 */
	public final void multiply(Vector2 other)
	{
		this.x *= other.x;
		this.y *= other.y;
	}

	/**
	 * Divides this vector.
	 * @param divider The divider.
	 */
	public final void divide(float divider)
	{
		// Make sure that we don't divide by zero.
		if (divider != 0.0f)
		{
			this.x /= divider;
			this.y /= divider;
		}
	}

	/**
	 * Divides this vector with another vector.
	 * @param other The other vector.
	 */
	public final void divide(Vector2 other)
	{
		// Make sure that we don't divide by zero.
		if (other.x != 0.0f && other.y != 0.0f)
		{
			this.x /= other.x;
			this.y /= other.y;
		}
	}

	/**
	 * Returns the length of this vector.
	 * This is also known as the magnitude of the vector.
	 * @return The length.
	 */
	public final float length()
	{
		return (float) Math.sqrt( (x * x) + (y * y) );
	}

	/**
	 * Normalizes the vector by dividing this vector with its magnitude.
	 * As a result of this the vector will have the magnitude of 1.
	 * @return The original length of the vector.
	 */
	public final float normalize()
	{
		final float magnitude = length();
		divide(magnitude);
		return magnitude;
	}

	/**
	 * Returns the dot product of this and another vector.
	 * @param other The other vector.
	 * @return The dot product.
	 */
	public final float dot(Vector2 other)
	{
		return (x * other.x) + (y * other.y);
	}

	/**
	 * Returns the distance from another vector.
	 * @param other The other vector.
	 * @return The distance.
	 */
	public final float distance(Vector2 other)
	{
		final float dx = x - other.x;
		final float dy = y - other.y;
		return (dx * dx) + (dy * dy);
	}

	public final Vector2 copy()
	{
		Vector2 copy = new Vector2();
		copy.set(this);
		return copy;
	}

	/**
	 * Sets this vector to zero.
	 */
	public final void zero()
	{
		x = 0.0f;
		y = 0.0f;
	}

	// -------------------
	// Getters and setters
	// -------------------

	/**
	 * Sets the magnitude for this vector.
	 * @param x The value on the x-axis.
	 * @param y The value on the y-axis.
	 */
	public void set(float x, float y)
	{
		this.x = x;
		this.y = y;
	}

	/**
	 * Sets the magnitude based on another vector.
	 * @param other The other vector.
	 */
	public void set(Vector2 other)
	{
		this.x = other.x;
		this.y = other.y;
	}
}
